package frc.robot.commands;

import java.io.IOException;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
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
      Neptune.elevator.resetYaw();
      Neptune.driveTrain.resetEncoders();
      try {
          left_trajectory = PathfinderFRC.getTrajectory("far_rocket.right");
          right_trajectory = PathfinderFRC.getTrajectory("far_rocket.left");
          System.out.println("Success!");
      } catch (IOException e) {
          e.printStackTrace();
      }
      m_left_follower = new EncoderFollower(left_trajectory);
      m_right_follower = new EncoderFollower(right_trajectory);
      m_left_follower.configureEncoder(Neptune.driveTrain.getLeftCounts(), 1365, 0.5);
      m_right_follower.configureEncoder(Neptune.driveTrain.getRightCounts(), 1365, 0.5);
      m_left_follower.configurePIDVA(0.01, 0, 0, 0.01, 0.001);
      m_right_follower.configurePIDVA(0.01, 0, 0, 0.01, 0.001);
      m_follower_notifier = new Notifier(this::followPath);
      m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);
  }

  protected void followPath() {
    if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
        m_follower_notifier.stop();
        finished = true;
    } else {
        double left_speed = m_left_follower.calculate(Neptune.driveTrain.getLeftCounts());
        double right_speed = m_right_follower.calculate(Neptune.driveTrain.getRightCounts());
        double heading = Neptune.elevator.getYaw();
        double desired_heading = -Pathfinder.r2d(m_left_follower.getHeading());
        double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
        double turn =  0.8 * (-1.0/80.0) * heading_difference;
        Neptune.driveTrain.openLoop(left_speed + turn, right_speed - turn);
        System.out.println("Left Speed: " + left_speed);
        System.out.println("Right Speed: " + right_speed);
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
