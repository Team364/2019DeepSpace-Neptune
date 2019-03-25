package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends Command {
  
  double min_aim_command = 0.05;
  double KpAim = -0.1;
  double KpDistance = -0.1;
  double left;
  double right;

  public Limelight() {
    requires(Neptune.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);

        double heading_error = -tx;
        double distance_error = -ty;
        double steering_adjust = 0.0;

        if (tx > 1.0){
            steering_adjust = KpAim*heading_error - min_aim_command;
        }else if (tx < 1.0){
            steering_adjust = KpAim*heading_error + min_aim_command;
        }
       double distance_adjust = KpDistance * distance_error;

        left += steering_adjust + distance_adjust;
        right -= steering_adjust + distance_adjust;

        Neptune.driveTrain.openLoop(left, right);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
