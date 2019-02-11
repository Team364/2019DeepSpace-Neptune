package frc.robot.oi;

import javax.sql.rowset.serial.SerialJavaObject;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subroutines.*;
import frc.robot.subroutines.*;
import frc.robot.util.States;
//import frc.robot.commands.teleop.TestPGyro;

public class OperatorOI{

    //Operator Controller
    //Xbox One Wired Controller
    /**
     * Index
     * <p>Xbox One Controller Axes
     * <p>0:Left Joystick X axis
     * <p>1:Left Joystick Y axis
     * <p>2:Left Trigger
     * <p>3:Right Trigger
     * <p>4:Right Joystick X axis
     * <p>5:Right Joystick Y axis
     * <p>
     * <p>Xbox One Controller Buttons
     * <p>1:Green A Button - Bottom Button
     * <p>2:Red B Button - Right Button
     * <p>3:Blue X Button - Left Button
     * <p>4:Yellow Y Button - Top Button
     * <p>5:Left Bumper Button - Above Left Trigger
     * <p>6:Right Bumper Buttom - Above Right Trigger
     * <p>7:Left Menu Button - Under Xbox Logo in the middle of controller
     * <p>8:Right Menu Button - Under Xbox Logo in the middle of controller
     * <p>9:Depressed Left Joystick
     * <p>10:Depressed Right Joystick
     * <p>
     * <p>POV - Directional Pad
     * <p>Top = 0
     * <p>Top + Left = 45
     * <p>Left = 90
     * <p>Bottom + Left = 135
     * <p>Bottom = 180
     * <p>Bottom + Right = 225
     * <p>Left = 270
     * <p>Top + Left = 315
     */
    public Joystick controller2;

    //Lift Buttons
    public JoystickButton setLiftPositionLow;
    public JoystickButton setLiftPositionMedium;
    public JoystickButton setLiftPositionHigh;
    public JoystickButton setLiftPositionCargo;

    public static Command IntakeCargo = new IntakeCargo();
    public static Command IntakeHatch = new IntakeHatch();
    public static Command ScoreCargo = new ScoreCargo();
    public static Command ScoreHatch = new ScoreHatch();


    //Operator Buttons
    /**
     * OperatorOI()
     * <p>Initializes Joysticks and buttons thereof
     * <p>assigns commands to buttons when pressed or held
     */
    public OperatorOI() {

        //Initialize Operator Controller
        controller2 = new Joystick(1);
        //Set state to cargo when left trigger is pulled
        // setObjectStateCargo = new JoystickTrigger(2);
        // setObjectStateCargo.whenActive(new SetObjectStateCargo());
        //Set Lift Position to level 1 for scoring in rocket and hatches on cargo ship
        setLiftPositionLow = new JoystickButton(controller2, 1);
        setLiftPositionLow.whenPressed(new Elevate(1));
        //Set Lift Position to level 2 for scoring in rocket
        setLiftPositionMedium = new JoystickButton(controller2, 2);
        setLiftPositionMedium.whenPressed(new Elevate(2));
        //Set Lift Position to level 3 for scoring in rocket
        setLiftPositionHigh = new JoystickButton(controller2, 4);
        setLiftPositionHigh.whenPressed(new Elevate(3));
        //Set Lift Position to level 4 for scoring Cargo in Cargo Ship
        setLiftPositionCargo = new JoystickButton(controller2, 3);
        setLiftPositionCargo.whenPressed(new Elevate(4));
    }
    /**
   * Sets objectState
   * <p>starts intakeObject
   * <p>starts scoreObject
   */
    public void controlLoop(){
  //Control Logic
    //Setting States
    //If Up on the D-pad is pressed,
    //Object state is set to Cargo
    //If Down on the D-pad is pressed,
    //Object state is set to Hatch
    if(controller2.getPOV() == 0){
        States.objState = States.ObjectStates.CARGO_OBJ;
      }else if(controller2.getPOV() == 180){
        States.objState = States.ObjectStates.HATCH_OBJ;
      }
      //If the right Trigger is pressed,
      //the robot will outtake
      //Before this executes,
      //it is checked whether or not the intake
      //object command is running because these
      //directly interfere with one another
      if(controller2.getRawAxis(3) >= 0.5){
        if(IntakeCargo.isRunning()){
          IntakeCargo.cancel();
        }else if(IntakeHatch.isRunning()){
          IntakeHatch.cancel();
        }
        if(States.objState == States.ObjectStates.CARGO_OBJ){
          ScoreCargo.start();
        }else if(States.objState == States.ObjectStates.HATCH_OBJ){
          ScoreHatch.start();
        }
      
      //If the left Trigger is pressed,
      //the robot will outtake
      //Before this executes,
      //it is checked whether or not the score
      //object command is running because these
      //directly interfere with one another
      }else if(controller2.getRawAxis(2) >= 0.5){
        if(ScoreCargo.isRunning()){
          ScoreCargo.cancel();
        }else if(ScoreHatch.isRunning()){
          ScoreHatch.cancel();
        }
        if(States.objState == States.ObjectStates.CARGO_OBJ){
          IntakeCargo.start();
        }else if(States.objState == States.ObjectStates.HATCH_OBJ){
          IntakeHatch.start();
        }
      }
    }
}

