/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.States;
/**Controls state logic for variable robot funtionality */
public class Periodic extends Command {

  public int loops = 0;
  private boolean[] Limits;
  private boolean passiveLatch;
  public Periodic() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.superStructure);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Limits = Robot.superStructure.limitArray; 
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

      //Update Limit Switches
      Limits[0] = !Robot.superStructure.iL.get();
      Limits[1] = false;
      Limits[2] = false;
      Limits[3] = false;
    
    //Loop State assignement
    if(States.loopState == States.LoopStates.CLOSED_LOOP){
      ++loops;
      if(loops > 20){
      // if(Robot.superStructure.arm.reachedPosition()||Robot.superStructure.lift.reachedPosition()){
        // if(Robot.superStructure.lift.reachedPosition()){
        if(Robot.superStructure.arm.reachedPosition()){
        States.loopState = States.LoopStates.OPEN_LOOP;
        loops = 0;
      }
    }
    }

    //Set the arm and lift back to start config
    if(Robot.superStructure.elevatorPassive() && !passiveLatch){
      // Elevate = new Elevate(1);
      // Elevate.start();
      System.out.println("Would move to neutral position");
      passiveLatch = Robot.superStructure.elevatorPassive();
    }
    //Drive Train Motion State Assignment
    // double rVel = Robot.superStructure.rightDrive.getVelocity();
    // double lVel = Robot.superStructure.leftDrive.getVelocity();
    // if((Math.abs(rVel) > 0) || (Math.abs(lVel) > 0)){
    //   States.driveMotionState = States.DriveMotionStates.MOVING;
    // }else if((rVel == 0)&&(lVel == 0)){
    //   States.driveMotionState = States.DriveMotionStates.NOT_MOVING;
    // }

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
