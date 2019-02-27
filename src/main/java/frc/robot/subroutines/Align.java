/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.misc.PIDCalc;

public class Align extends Command {

  private PIDCalc alignPID = new PIDCalc(0.01, 0.0, 0.0, 0.0, "Align");

  public Align() {
    requires(Neptune.driveTrain);
    alignPID.setTolerance(10);
    setInterruptible(true);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    alignPID.resetPID();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double pidOutputNavX = alignPID.calculateOutput(480, Neptune.vision.getCenterXValues()[0]);
    Neptune.driveTrain.openLoop(-pidOutputNavX, pidOutputNavX);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return alignPID.onTarget() || (Neptune.vision.getCenterXValues()[0] == 0);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Neptune.driveTrain.stop();
    alignPID.resetPID();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
