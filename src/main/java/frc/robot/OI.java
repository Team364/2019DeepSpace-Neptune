package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.auto.drive.ShiftDown;
import frc.robot.commands.auto.drive.ShiftUp;
import frc.robot.commands.teleop.Subroutines.TeleopTurn180;
import frc.robot.commands.teleop.TeleopAlignWithTape;
import frc.robot.commands.teleop.TeleopFollowBall;


public class OI {

    public Joystick controller;

    public JoystickButton shiftLow;
    public JoystickButton shiftHigh;
    public JoystickButton turn180Button;
    public JoystickButton alignWithTapeButton;
    public JoystickButton followCubeButton;

    public OI() {

        controller = new Joystick(0);

        shiftLow = new JoystickButton(controller, 7);
        shiftLow.whenPressed(new ShiftDown());

        shiftHigh = new JoystickButton(controller, 8);
        shiftHigh.whenPressed(new ShiftUp());

        turn180Button = new JoystickButton(controller, 1);
        turn180Button.whenPressed(new TeleopTurn180());

        alignWithTapeButton = new JoystickButton(controller, 2);
        alignWithTapeButton.whileActive(new TeleopAlignWithTape());

        followCubeButton = new JoystickButton(controller, 10);
        followCubeButton.whenPressed(new TeleopFollowBall());
    }
}
