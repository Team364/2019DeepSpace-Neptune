package frc.robot.oi;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Neptune;
import frc.robot.commands.*;
import frc.robot.subroutines.*;
import edu.wpi.first.wpilibj.command.Command;
public class DriverOI {
    //Driver Controller
    //Xbox One Wired Controller
    public Joystick controller;


    //Driver Buttons
    public JoystickButton shiftLow;
    public JoystickButton shiftHigh;
    public JoystickButton aim;

    public JoystickButton climb;
    public JoystickButton climb2;
    public JoystickButton cancelClimb;
    public Command lvl3ClimbSequence = new Climb(3);
    public Command lvl2ClimbSequence = new Climb(2);

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

        aim = new JoystickButton(controller, 1);
        aim.whileActive(new LimeDrive());

        climb = new JoystickButton(controller, 8);
        climb.whenPressed(lvl3ClimbSequence);

        climb2 = new JoystickButton(controller, 7);
        climb2.whenPressed(lvl2ClimbSequence);
        
        cancelClimb = new JoystickButton(controller, 4);
        cancelClimb.cancelWhenPressed(lvl3ClimbSequence);
        cancelClimb.cancelWhenPressed(lvl2ClimbSequence);

        
    }

}
