package frc.robot.oi;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Neptune;
import frc.robot.commands.SetPiston;
import frc.robot.commands.*;
import frc.robot.subroutines.climb;
import frc.robot.subroutines.climbDrive;
import frc.robot.subroutines.retractClimb;
import frc.robot.subroutines.*;
public class DriverOI {
    //Driver Controller
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
    public Joystick controller;

    /**
     * 
     * Operator Controller
     */
    public Joystick controller2;

    //Driver Buttons
    public JoystickButton shiftLow;
    public JoystickButton shiftHigh;
    public JoystickButton turn180Button;
    public JoystickButton align;

    public JoystickButton Climb;
    public JoystickButton ClimbDrive;
    public JoystickButton retractClimb;
    public JoystickButton climbElevate;
    public JoystickButton undoClimbFrontButton;

    /**
     * OI()
     * <p>Initializes Joysticks and buttons thereof
     * <p>assigns commands to buttons when pressed or held
     */
    public DriverOI() {
        controller = new Joystick(0);
  
        shiftLow = new JoystickButton(controller, 5);
        shiftLow.whenPressed(new SetPiston(Neptune.driveTrain.shifter, 1));

        shiftHigh = new JoystickButton(controller, 6);
        shiftHigh.whenPressed(new SetPiston(Neptune.driveTrain.shifter, 0));

        align = new JoystickButton(controller, 2);
        align.whenPressed(new Align());
        //align.whileHeld(new Align());

        // Climb = new JoystickButton(controller, 4);
        // Climb.whileHeld(new climb());
        // ClimbDrive = new JoystickButton(controller, 7);
        // ClimbDrive.whileHeld(new climbDrive());
        // retractClimb = new JoystickButton(controller, 2);
        // retractClimb.whileHeld(new retractClimb());
        // climbElevate = new JoystickButton(controller, 1);    
        // climbElevate.whenPressed(new climbElevate());

    }
}
