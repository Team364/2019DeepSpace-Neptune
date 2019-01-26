package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.auto.drive.ShiftDown;
import frc.robot.commands.auto.drive.ShiftUp;
import frc.robot.commands.teleop.Subroutines.TeleopTurn180;
import frc.robot.commands.teleop.TeleopAlignWithTape;
import frc.robot.commands.teleop.TeleopCenterOnBall;
import frc.robot.commands.teleop.TeleopAlignWithDisk;
import frc.robot.commands.teleop.TestPGyro;

public class OI {

    public Joystick controller;

    public JoystickButton shiftLow;
    public JoystickButton shiftHigh;
    public JoystickButton turn180Button;
    public JoystickButton alignWithTapeButton;
    public JoystickButton followCubeButton;
    public JoystickButton alignWithDiskButton;

    public JoystickButton diagnosticButton;

    public OI() {

        controller = new Joystick(0);

        shiftLow = new JoystickButton(controller, 5);
        shiftLow.whenPressed(new ShiftDown());

        shiftHigh = new JoystickButton(controller, 6);
        shiftHigh.whenPressed(new ShiftUp());

        turn180Button = new JoystickButton(controller, 1);
        turn180Button.whenPressed(new TeleopTurn180());

        alignWithTapeButton = new JoystickButton(controller, 2);
        alignWithTapeButton.whileActive(new TeleopAlignWithTape());

        followCubeButton = new JoystickButton(controller, 3);
        followCubeButton.whileActive(new TeleopCenterOnBall());

        alignWithDiskButton = new JoystickButton(controller, 4);
        alignWithDiskButton.whenPressed(new TeleopAlignWithDisk());

        diagnosticButton = new JoystickButton(controller, 7);
        // diagnosticButton.whileActive(new TestPGyro());


    }
}
