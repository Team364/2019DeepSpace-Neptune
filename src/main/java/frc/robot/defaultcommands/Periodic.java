/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subroutines.*;
import frc.robot.util.States;
/**Controls state logic for variable robot funtionality */
public class Periodic extends Command {
  public int loops = 0;
  private Command Elevate;
  public Periodic() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.superStructure);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Loop State assignement
    if(States.loopState == States.LoopStates.CLOSED_LOOP){
      ++loops;
      if(loops > 20){
      if(Robot.superStructure.arm.reachedPosition()||Robot.superStructure.lift.reachedPosition()){
        States.loopState = States.LoopStates.OPEN_LOOP;
        loops = 0;
      }
    }
    }
    //If a ball is in stow then the action state is ferry
    if((Robot.superStructure.limitArray[0])||(Robot.superStructure.lever.pistonState.toString() == "OPEN")){
      States.actionState = States.ActionStates.FERRY_ACT;
    }else if(!(States.actionState == States.ActionStates.INTAKE_ACT)&&!(States.actionState == States.ActionStates.SCORE_ACT)){
      States.actionState = States.ActionStates.NO_ACT;
    }
    /*If nothing is being scored, the intake is not running, and no game peice is possessed,
    then the lift and arm are to assume the intake position
    As of now there is not one, so it is going to be set to 1
    0 will be the integer associated with the intake position*/
    if(States.actionState == States.ActionStates.NO_ACT){
      Elevate = new Elevate(1);
      Elevate.start();
    }
    //Drive Train Motion State Assignment
    double rVel = Robot.superStructure.rightDrive.getVelocity();
    double lVel = Robot.superStructure.leftDrive.getVelocity();
    if((Math.abs(rVel) > 0) || (Math.abs(lVel) > 0)){
      States.driveMotionState = States.DriveMotionStates.MOVING;
    }else if((rVel == 0)&&(lVel == 0)){
      States.driveMotionState = States.DriveMotionStates.NOT_MOVING;
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
