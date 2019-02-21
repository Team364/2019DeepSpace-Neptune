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
    public JoystickButton setIntakePosition;
    
    private boolean cargo;
    private boolean intake;

    public int gripSet = 1;


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
        controller2 = new Joystick(1);
        //Set state to cargo when left trigger is pulled
        // setObjectStateCargo = new JoystickTrigger(2);
        // setObjectStateCargo.whenActive(new SetObjectStateCargo());
        //Set Lift Position to level 1 for scoring in rocket and hatches on cargo ship
        setLiftPositionLow = new JoystickButton(controller2, 1);
        setLiftPositionLow.whenPressed(new ElevateToPosition(1));
        //Set Lift Position to level 2 for scoring in rocket
        setLiftPositionMedium = new JoystickButton(controller2, 2);
        setLiftPositionMedium.whenPressed(new ElevateToPosition(2));
        //Set Lift Position to level 3 for scoring in rocket
        setLiftPositionHigh = new JoystickButton(controller2, 4);
        setLiftPositionHigh.whenPressed(new ElevateToPosition(3));
        //Set Lift Position to level 4 for scoring Cargo in Cargo Ship
        setLiftPositionCargo = new JoystickButton(controller2, 3);
        setLiftPositionCargo.whenPressed(new ElevateToPosition(4));
        //Set Lift Position to level 0 for intaking
        setIntakePosition = new JoystickButton(controller2, 5);
        setIntakePosition.whenPressed(new setIntakePos());
    }

    
}

