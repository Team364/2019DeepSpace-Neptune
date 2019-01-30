package frc.robot.oi;

import javax.sql.rowset.serial.SerialJavaObject;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.auto.drive.ShiftDown;
import frc.robot.commands.auto.drive.ShiftUp;
import frc.robot.commands.teleop.buttonsubroutines.drive.*;
import frc.robot.commands.teleop.buttonsubroutines.*;
import frc.robot.commands.teleop.activesubroutines.*;
import frc.robot.commands.teleop.buttonsubroutines.lift.*;
import frc.robot.oi.controls.*;
import frc.robot.commands.teleop.buttonsubroutines.*;
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

    public Double leftStick;
    //Lift Buttons
    public JoystickButton setLiftPositionLow;
    public JoystickButton setLiftPositionMedium;
    public JoystickButton setLiftPositionHigh;

    //Ball
    public JoystickTrigger setObjectStateCargo;
    //Disk
    public JoystickButton setObjectStateHatch;


    //Operator Buttons
    /**
     * OperatorOI()
     * <p>Initializes Joysticks and buttons thereof
     * <p>assigns commands to buttons when pressed or held
     */
    public OperatorOI() {

        //Initialize Operator Controller
        controller2 = new Joystick(1);
        leftStick = controller2.getRawAxis(1);
        //Set state To hatch when left bumper is pressed
        setObjectStateHatch = new JoystickButton(controller2, 5);
        setObjectStateHatch.whenPressed(new SetObjectStateHatch());
        //Set state to cargo when left trigger is pulled
        setObjectStateCargo = new JoystickTrigger(2);
        setObjectStateCargo.whenPressed(new SetObjectStateCargo());
        //Set Lift Position to level 1
        setLiftPositionLow = new JoystickButton(controller2, 1);
        setLiftPositionLow.whenPressed(new ElevateToPosition(1));
        //Set Lift Position to level 2
        setLiftPositionMedium = new JoystickButton(controller2, 2);
        setLiftPositionMedium.whenPressed(new ElevateToPosition(2));
        //Set Lift Position to level 3
        setLiftPositionHigh = new JoystickButton(controller2, 4);
        setLiftPositionHigh.whenPressed(new ElevateToPosition(3));

        
    }
}

