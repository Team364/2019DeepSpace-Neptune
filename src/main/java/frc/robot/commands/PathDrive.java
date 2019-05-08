package frc.robot.commands;

import java.io.IOException;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

public class PathDrive extends Command {

    private Notifier m_follower_notifier;
    private Trajectory left_trajectory;
    private Trajectory right_trajectory;
    private EncoderFollower m_left_follower;
    private EncoderFollower m_right_follower;
    private boolean finished = false;

    public PathDrive() {
        requires(Neptune.driveTrain);
    }

    @Override
    protected void initialize() {
      Neptune.elevator.resetYaw(0);
      Neptune.driveTrain.resetEncoders();
      try {
          left_trajectory = PathfinderFRC.getTrajectory("front_cargo.right");
          right_trajectory = PathfinderFRC.getTrajectory("front_cargo.left");
          System.out.println("Success!");
      } catch (IOException e) {
          e.printStackTrace();
      }
      // Create new EncoderFollower objects with correct trajectories
      m_left_follower = new EncoderFollower(left_trajectory);
      m_right_follower = new EncoderFollower(right_trajectory);

      // Configure EncoderFollower for 1365 counts/rev encoder and 6 inch wheels
      m_left_follower.configureEncoder(Neptune.driveTrain.getLeftCounts(), 1365, 0.5);
      m_right_follower.configureEncoder(Neptune.driveTrain.getRightCounts(), 1365, 0.5);

      // Config kP (pos FB) and kV (vel FF)
      m_left_follower.configurePIDVA(1.0, 0, 0, 1 / 14.2, 0); // 0.003, 0, 0, 0.0875, 0
      m_right_follower.configurePIDVA(1.0, 0, 0, 1 / 14.2, 0); // Set the kV to 1 / MAX_VEL

      // Start notifier to make sure that the path is run at 20ms
      m_follower_notifier = new Notifier(this::followPath);
      m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);
  }

  protected void followPath() {
    if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
        m_follower_notifier.stop();
        finished = true;
    } else {
        // Get Left and Right (current) velocities
        double left_speed = m_left_follower.calculate(Neptune.driveTrain.getLeftCounts());
        double right_speed = m_right_follower.calculate(-Neptune.driveTrain.getRightCounts());

        // Get heading and determine heading correction
        double heading = Neptune.elevator.getYaw();
        double desired_heading = -Pathfinder.r2d(m_left_follower.getHeading());
        double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
        double turn =  0.8 * (-1.0/80.0) * heading_difference;

        // Apply motor power
        Neptune.driveTrain.openLoop(left_speed + turn, right_speed - turn);

        // Print "velocity" from calculation
        SmartDashboard.putNumber("Left Power", left_speed);
        SmartDashboard.putNumber("Right Power", right_speed);
        SmartDashboard.putNumber("Left Speed", Neptune.driveTrain.getLeftCounts());
        SmartDashboard.putNumber("Right Speed", -Neptune.driveTrain.getRightCounts());
    }
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return finished;
  }

  @Override
  protected void end() {
      Neptune.driveTrain.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }

}
