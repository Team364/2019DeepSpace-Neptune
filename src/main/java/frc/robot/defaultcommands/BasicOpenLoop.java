/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.BasicTalon;
import frc.robot.Robot;

public class BasicOpenLoop extends Command {

  private double deadband;
  private int axis;
  private BasicTalon talon;
  public BasicOpenLoop(BasicTalon talon, int axis, double deadband) {
    this.talon = talon;
    this.deadband = deadband;
    this.axis = axis;
  }

  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double power = Robot.oi2.controller2.getRawAxis(axis);
      if(Math.abs(power) >= deadband){
        talon.openLoop(power);
    }else{
        talon.stop();
  }
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
