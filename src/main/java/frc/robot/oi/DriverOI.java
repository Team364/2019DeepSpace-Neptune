package frc.robot.oi;

import static frc.robot.RobotMap.CARGOGYROSET;
import static frc.robot.RobotMap.INTAKEGYROSET;
import static frc.robot.RobotMap.LIMEBUTTONCARGO;
import static frc.robot.RobotMap.LIMEBUTTONINTAKE;
import static frc.robot.RobotMap.LIMEBUTTONROCKET;
import static frc.robot.RobotMap.RESETGYRO;
import static frc.robot.RobotMap.ROCKETGYROSET;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subroutines.Climb;
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
    public JoystickButton climb;
    public JoystickButton cancelClimb;

    
    public Command lvl3ClimbSequence = new Climb(3);


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

        climb = new JoystickButton(controller, 8);
        climb.whenPressed(lvl3ClimbSequence);
        

        cancelClimb = new JoystickButton(controller, 4);
        cancelClimb.cancelWhenPressed(lvl3ClimbSequence);



    }

}
