/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.commands.SetPiston;

public class climbElevate extends Command {
  private Command flipem = new SetPiston(Neptune.elevator.front, 0);
  public climbElevate() {
    requires(Neptune.elevator);
    setTimeout(1);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Neptune.elevator.elevateTo(80000, 1500);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    flipem.start();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
