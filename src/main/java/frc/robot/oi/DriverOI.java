package frc.robot.oi;

import static frc.robot.RobotMap.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;

public class DriverOI {
    //Driver Controller
    //Xbox One Wired Controller
    public Joystick controller;

    public JoystickButton limeAimCargo;
    public JoystickButton limeAimRocket;
    public JoystickButton limeAimIntake;


    public JoystickButton resetGyro;
    public JoystickButton resetMods;
    /**
     * OI()
     * <p>Initializes Joysticks and buttons thereof
     * <p>assigns commands to buttons when pressed or held
     */
    public DriverOI() {
        controller = new Joystick(0);

        limeAimCargo = new JoystickButton(controller, LIMEBUTTONCARGO);
        limeAimCargo.whileHeld(new LimeAim(CARGOGYROSET));

        limeAimRocket = new JoystickButton(controller, LIMEBUTTONROCKET);
        limeAimRocket.whileHeld(new LimeAim(ROCKETGYROSET));
        
        limeAimIntake = new JoystickButton(controller, LIMEBUTTONINTAKE);
        limeAimIntake.whileHeld(new LimeAim(INTAKEGYROSET));

        resetGyro = new JoystickButton(controller, RESETGYRO);
        resetGyro.whileHeld(new ResetGyro());

        resetMods = new JoystickButton(controller, RESETMODS);
        resetMods.whileHeld(new ResetMods());

    }

}
