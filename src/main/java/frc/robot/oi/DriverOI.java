package frc.robot.oi;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Neptune;
import frc.robot.commands.*;
import frc.robot.subroutines.*;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.defaultcommands.DriveOpenLoop;
public class DriverOI {
    //Driver Controller
    //Xbox One Wired Controller
    public Joystick controller;

    /**
     * OI()
     * <p>Initializes Joysticks and buttons thereof
     * <p>assigns commands to buttons when pressed or held
     */
    public DriverOI() {
        controller = new Joystick(0);
  


    }

}
