/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;
import frc.robot.defaultcommands.ElevatorManual;
import frc.robot.subroutines.*;
import frc.robot.States;
import frc.robot.commands.*;

public class TridentControlLoop extends Command {
  public int gripSet = 1;
  private boolean cargo;
  private boolean intake;
  private Command runGrip;
  private Command elevate;
  private Command setLever;
  private Command setClaw;
  private Command liftManual = new ElevatorManual();
  public TridentControlLoop() {
      requires(Neptune.trident);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
       /*Setting States
    If Up on the D-pad is pressed,
    Object state is set to Cargo
    If Down on the D-pad is pressed,
    Object state is set to Hatch*/
    if(Neptune.oi2.controller2.getPOV() == 0){
      States.objState = States.ObjectStates.CARGO_OBJ;
    }else if(Neptune.oi2.controller2.getPOV() == 180){
      States.objState = States.ObjectStates.HATCH_OBJ;
    }
    if(Neptune.oi2.controller2.getRawAxis(2) >= 0.5){
      States.actionState = States.ActionStates.INTAKE_ACT;
    }else if(Neptune.oi2.controller2.getRawAxis(3) >= 0.5){
      States.actionState = States.ActionStates.SCORE_ACT;
    }else if(Neptune.oi2.controller2.getRawButton(5)){
      States.actionState = States.ActionStates.SEEK;
    }else{
      if(((gripSet == 3)||(gripSet == 4)) && Neptune.trident.tridentInactive()){
        States.actionState = States.ActionStates.PASSIVE;  
      }else if(Neptune.trident.tridentInactive()){
        States.actionState = States.ActionStates.FERRY_ACT;
      }

    }
    /**Sets action state for scoring and then runs the grip subroutine */
    if(States.objState == States.ObjectStates.CARGO_OBJ) {cargo = true;}
    else if(States.objState == States.ObjectStates.HATCH_OBJ) {cargo = false;}
    if(States.actionState == States.ActionStates.SCORE_ACT) {intake = false;}
    else if(States.actionState == States.ActionStates.INTAKE_ACT) {intake = true;}
    if(cargo && intake){gripSet = 1;}//Get Cargo
    else if(!cargo && intake){gripSet = 2;}//Get Hatch
    else if(cargo && !intake){gripSet = 3;} //Score Cargo
    else if(!cargo && !intake){gripSet  = 4;} //Score Hatch 
    else{gripSet = 0;}//Should never happen

    
    if((Neptune.oi2.controller2.getRawAxis(3) >= 0.5)||(Neptune.oi2.controller2.getRawAxis(2) >= 0.5)){
      runGrip = new RunGrip(gripSet);
      runGrip.start();
    }
    SmartDashboard.putNumber("Grip Set: ", gripSet);
    if((Math.abs(Neptune.oi2.controller2.getRawAxis(1)) > 0.2)){
      liftManual.start();
    } 
    //If RB is hit then the elevator goes to start config
    if(Neptune.oi2.controller2.getRawButton(6)){
      elevate = new ElevateToPosition(5);
      elevate.start();
      if(States.objState == States.ObjectStates.HATCH_OBJ){
        setLever = new SetPiston(Neptune.trident.lever, 0);
        setClaw = new SetPiston(Neptune.trident.claw, 0);
      }else if(States.objState == States.ObjectStates.CARGO_OBJ){
        setLever = new SetPiston(Neptune.trident.lever, 1);
        setClaw = new SetPiston(Neptune.trident.claw, 1);
      }

      setLever.start();
      setClaw.start();

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
