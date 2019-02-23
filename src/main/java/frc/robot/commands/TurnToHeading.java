/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;
import frc.robot.misc.PIDCalc;

public class TurnToHeading extends Command {

  private PIDCalc GyroPID;
  private double heading;
  
  public TurnToHeading(double heading) {
      requires(Neptune.driveTrain);
      GyroPID = new PIDCalc(0.05, 0.0, 0.0, 0.0, "NavX");
      GyroPID.setTolerance(2);
      this.heading = heading;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Neptune.driveTrain.zeroGyro();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    double pidOutputNavX = GyroPID.calculateOutput(heading, Neptune.driveTrain.getGyroAngle());
    Neptune.driveTrain.openLoop(pidOutputNavX, -pidOutputNavX);
    SmartDashboard.putNumber("Output: ", pidOutputNavX);
    SmartDashboard.putNumber("Gyro Error", GyroPID.getError());
    SmartDashboard.putBoolean("On Target", GyroPID.onTarget());
    SmartDashboard.putNumber("GyroAngle: ", Neptune.driveTrain.getGyroAngle());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return GyroPID.onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Neptune.driveTrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
