/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.States;
import frc.robot.util.prefabs.commands.*;
/**Controls state logic for variable robot funtionality */
public class Periodic extends Command {

  public int loops = 0;
  private boolean[] Limits;
  private boolean passiveLatch = false;
  private Command stopLift = new Stop(Robot.superStructure.lift);

  public Periodic() {
    requires(Robot.superStructure);
  }

  @Override
  protected void initialize() {
    Limits = Robot.superStructure.limitArray; 
  }

  @Override
  protected void execute() {

    //Update Limit Switches
      Limits[0] = Robot.superStructure.iL.get();//True when pressed
      Limits[1] = Robot.superStructure.aL.get();//True when pressed
      Limits[2] = Robot.superStructure.lLL.get();
      Limits[3] = Robot.superStructure.uLL.get();
    //Track Lift Zone
    if((Robot.superStructure.lift.getPosition() > -10000) &&(Robot.superStructure.lift.getPosition() < RobotMap.liftLowerBound)){
      States.liftZone = States.LiftZones.LOWER_DANGER;
    }else if((Robot.superStructure.lift.getPosition() < -100000)&&(Robot.superStructure.lift.getPosition() > RobotMap.liftUpperBound))
      States.liftZone = States.LiftZones.UPPER_DANGER;
    else{
      States.liftZone = States.LiftZones.SAFE;
    }
    
    if((Robot.superStructure.lift.getPosition() <= RobotMap.liftUpperBound)){
      stopLift.start();
    }
    //Loop State assignement
    if(States.loopState == States.LoopStates.CLOSED_LOOP){
      if(Limits[3]||Limits[2]){
        stopLift.start();
      }
      ++loops;
      if(loops > 20){
       if(Robot.superStructure.arm.reachedPosition()||Robot.superStructure.lift.reachedPosition()){
        // if(Robot.superStructure.lift.reachedPosition()){ -- testing lift alone
        //if(Robot.superStructure.arm.reachedPosition()){ --testing arm alone 
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
