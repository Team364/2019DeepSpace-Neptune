package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

    public Joystick operationStation;
    public Joystick controller;
    public Joystick driverController;

    public JoystickButton shiftLow;
    public JoystickButton shiftHigh;

    public JoystickButton clawButton;
    public JoystickButton pinchButton;
    public JoystickButton intakeButton;
    public JoystickButton outtakeButton;
    public JoystickButton firstStageLiftButton;
    public JoystickButton resetLiftEncoderButton;
    public JoystickButton dataButton;

    public JoystickButton autoSelectorButton;
    public JoystickButton flippyShitButton;

    public OI() {

        controller = new Joystick(0);
        shiftLow = new JoystickButton(controller, 7);
        shiftHigh = new JoystickButton(controller, 8);

        clawButton = new JoystickButton(controller, 5);
        pinchButton = new JoystickButton(controller, 6);
        firstStageLiftButton = new JoystickButton(controller, 4);//was 4
        resetLiftEncoderButton = new JoystickButton(controller, 1);
       
    }
}
