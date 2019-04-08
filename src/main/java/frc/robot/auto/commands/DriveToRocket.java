package frc.robot.auto.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

public class DriveToRocket extends Command {
  double left;
  double right;
  boolean flow;
  public DriveToRocket(double left, double right, double timeout, boolean flow) {
    requires(Neptune.driveTrain);
    setTimeout(timeout);
    this.left = left;
    this.right = right;
    this.flow = flow;

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Neptune.driveTrain.stop();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Neptune.driveTrain.openLoop(left, right);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    if(!flow){
      Neptune.driveTrain.stop();
    }

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
