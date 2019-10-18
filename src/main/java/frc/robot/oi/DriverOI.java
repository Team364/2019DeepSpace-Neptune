package frc.robot.oi;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Neptune;
import frc.robot.commands.*;
import frc.robot.subroutines.*;
import edu.wpi.first.wpilibj.command.Command;
import static frc.robot.RobotMap.*;
public class DriverOI {
    //Driver Controller
    //Xbox One Wired Controller
    public Joystick controller;

    public JoystickButton limeAim;
    public JoystickButton resetGyro;
    /**
     * OI()
     * <p>Initializes Joysticks and buttons thereof
     * <p>assigns commands to buttons when pressed or held
     */
    public DriverOI() {
        controller = new Joystick(0);

        limeAim = new JoystickButton(controller, LIMEBUTTON);
        limeAim.whileHeld(new LimeAim());

        resetGyro = new JoystickButton(controller, RESETGYRO);
        resetGyro.whileHeld(new ResetGyro());

    }

}
