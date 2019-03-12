package frc.robot.oi;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;
import frc.robot.defaultcommands.ElevatorManual;
import frc.robot.subroutines.*;
import frc.robot.States;
import frc.robot.commands.*;

public class OperatorOI{

    public Joystick buttoBoxo;

    //Lift Buttons
    public JoystickButton setLiftPositionLow;
    public JoystickButton setLiftPositionMedium;
    public JoystickButton setLiftPositionHigh;
    public JoystickButton setLiftPositionCargo;
    public JoystickButton setIntakePosition;
    public JoystickButton defense;
    
    private boolean cargo;
    private boolean intake;

    public int tridentCase = 1;


    private Command runGrip;
    private Command liftManual = new ElevatorManual();
    private Command elevate;
    private Command setLever;
    private Command setClaw;
    //Operator Buttons
    /**
     * OperatorOI()
     * <p>Initializes Joysticks and buttons thereof
     * <p>assigns commands to buttons when pressed or held
     */
    public OperatorOI() {

        //Initialize Operator Controller
        buttoBoxo = new Joystick(1);
        //Set Lift Position to level 1 for scoring in rocket and hatches on cargo ship
        setLiftPositionLow = new JoystickButton(buttoBoxo, 8);
        setLiftPositionLow.whenPressed(new ElevateToPosition(1));
        //Set Lift Position to level 2 for scoring in rocket
        setLiftPositionMedium = new JoystickButton(buttoBoxo, 9);
        setLiftPositionMedium.whenPressed(new ElevateToPosition(2));
        //Set Lift Position to level 3 for scoring in rocket
        setLiftPositionHigh = new JoystickButton(buttoBoxo, 10);
        setLiftPositionHigh.whenPressed(new ElevateToPosition(3));
        //Set Lift Position to level 4 for scoring Cargo in Cargo Ship
        setLiftPositionCargo = new JoystickButton(buttoBoxo, 7);
        setLiftPositionCargo.whenPressed(new ElevateToPosition(4));
        //Set Lift Position to level 0 for intaking
        setIntakePosition = new JoystickButton(buttoBoxo, 3);
        setIntakePosition.whenPressed(new SetIntakePos());

    }
    /**
   * Loop responsible for handling buttons
   * seperately depending on cargo or hatch mode
   */
    public void controlLoop(){

      //State Buttons
    if(buttoBoxo.getRawButton(1)){
        States.objState = States.ObjectStates.CARGO_OBJ;
      }else if(buttoBoxo.getRawButton(2)){
        States.objState = States.ObjectStates.HATCH_OBJ;
      }
      //Action Buttons
      if(buttoBoxo.getRawButton(4)){//Intake
        States.actionState = States.ActionStates.INTAKE_ACT;
      }else if(buttoBoxo.getRawButton(5)){//Score
        States.actionState = States.ActionStates.SCORE_ACT;
      }else if(buttoBoxo.getRawButton(3)){//Intake Position
        States.actionState = States.ActionStates.SEEK;
      }else{//If the robot is neither intaking, scoring, nor getting ready to intake then the robot
            //should either have an object or be empty
        if(((tridentCase == 3)||(tridentCase == 4)) && Neptune.trident.tridentInactive()){
          States.actionState = States.ActionStates.FERRY_ACT;  
        }else if(Neptune.trident.tridentInactive()){
          States.actionState = States.ActionStates.PASSIVE;
        }
 
      }
      /**Sets action state for scoring and then runs the grip subroutine */
      if(States.objState == States.ObjectStates.CARGO_OBJ){
        cargo = true;
      }else if(States.objState == States.ObjectStates.HATCH_OBJ){
        cargo = false;
      }if(States.actionState == States.ActionStates.SCORE_ACT){
        intake = false;
      }else if(States.actionState == States.ActionStates.INTAKE_ACT){
        intake = true;
      }

      if(cargo && intake){//Get Cargo
        tridentCase = 1;
      }else if(!cargo && intake){//Get Hatch
        tridentCase = 2;
      }else if(cargo && !intake){//Score Cargo
        tridentCase = 3;
      }else if(!cargo && !intake){//Score Hatch 
        tridentCase  = 4;
      }else{//Should never happen
        tridentCase = 0;
      }

      
      if((buttoBoxo.getRawButton(5))||(buttoBoxo.getRawButton(4))){
        runGrip = new ActivateTrident(tridentCase);
        runGrip.start();
      }
      SmartDashboard.putNumber("Grip Set: ", tridentCase);
      if((Math.abs(buttoBoxo.getRawAxis(1)) > 0.2)){
        liftManual.start();
      } 
      
      //Retract Button
      if(buttoBoxo.getRawButton(6)||Neptune.oi.controller.getRawButton(1)){
        elevate = new ElevateToPosition(5);
        elevate.start();
        setLever = new SetPiston(Neptune.trident.lever, 0);
        setClaw = new SetPiston(Neptune.trident.claw, 0);
        setLever.start();
        setClaw.start();

      }

      if(buttoBoxo.getRawButton(11)){
        elevate = new ElevateToPosition(11);
        elevate.start();
        setLever = new SetPiston(Neptune.trident.lever, 0);
        setClaw = new SetPiston(Neptune.trident.claw, 0);
        setLever.start();
        setClaw.start();

      }
    }
}

