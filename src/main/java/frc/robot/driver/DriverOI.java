package frc.robot.driver;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.driver.commands.drive.*;
import frc.robot.shared.oi.controls.*;
import frc.robot.driver.subroutines.pressed.drive.*;
import frc.robot.driver.subroutines.held.*;
//import frc.robot.commands.teleop.TestPGyro;

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
     * Operator Controller
     */
    public Joystick controller2;

    //Driver Buttons
    public JoystickButton shiftLow;
    public JoystickButton shiftHigh;
    public JoystickButton turn180Button;
    public JoystickButton alignWithTapeButton;
    public JoystickButton followCubeButton;
    public JoystickButton alignWithDiskButton;
    public JoystickButton diagnosticButton;

    public JoystickPOV left180;
    public JoystickPOV right180;
    /**
     * OI()
     * <p>Initializes Joysticks and buttons thereof
     * <p>assigns commands to buttons when pressed or held
     */
    public DriverOI() {
        //Driver controller
        controller = new Joystick(0);
  
        //Robot shifts gears down
        //Button is pressed once
        //Left menu button
        shiftLow = new JoystickButton(controller, 5);
        shiftLow.whenPressed(new ShiftDown());

        //Robot shifts gears up
        //Button is pressed once
        //Right menu button
        shiftHigh = new JoystickButton(controller, 6);
        shiftHigh.whenPressed(new ShiftUp());

        //Robot uses NavX Gyro to turn approximately 180 degrees(yaw)
        //Button is pressed once
        //Green A button
        turn180Button = new JoystickButton(controller, 1);
        turn180Button.whenPressed(new TeleopTurn180());

        //Does nothing
        //Button is held to run command
        //Cancelled when driver lets go of button - Keep in mind that Buttons that stick can cause issue
        //Red B button
        alignWithTapeButton = new JoystickButton(controller, 2);
        alignWithTapeButton.whileActive(new Align());


        //Does nothing
        //Use for testing and printing out data
        diagnosticButton = new JoystickButton(controller, 7);
        //diagnosticButton.whileActive(new TestPGyro());

        // //Right on the D-pad
        // right180 = new JoystickPOV(90);
        // right180.whenActive(new Turn180right());

        // //Left on the D-pad
        // left180 = new JoystickPOV(270);
        // left180.whenActive(new Turn180left());


    }
}
