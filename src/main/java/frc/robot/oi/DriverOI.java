package frc.robot.oi;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Neptune;
import frc.robot.commands.*;
import frc.robot.subroutines.*;
import edu.wpi.first.wpilibj.command.Command;
public class DriverOI {
    //Driver Controller
    public Joystick controller;

    //Driver Buttons
    public JoystickButton shiftLow;
    public JoystickButton shiftHigh;
    public JoystickButton turn180Button;
    public JoystickButton setUpIntermediateClimb;
    public JoystickButton intermediateClimb;

    public JoystickButton climb;
    public JoystickButton climb2;
    public JoystickButton cancelClimb;
    public Command lvl3ClimbSequence = new Climb(3);
    public Command lvl2ClimbSequence = new Climb(2);
    public JoystickButton lime;

    public DriverOI() {
        controller = new Joystick(0);
  
        shiftLow = new JoystickButton(controller, 5);
        shiftLow.whenPressed(new SetPiston(Neptune.driveTrain.shifter, 1));

        shiftHigh = new JoystickButton(controller, 6);
        shiftHigh.whenPressed(new SetPiston(Neptune.driveTrain.shifter, 0));

        lime = new JoystickButton(controller, 1);
        lime.whileActive(new LimeDrive());

        climb = new JoystickButton(controller, 8);
        climb.whenPressed(lvl3ClimbSequence);

        climb2 = new JoystickButton(controller, 7);
        climb2.whenPressed(lvl2ClimbSequence);
        
        cancelClimb = new JoystickButton(controller, 4);
        cancelClimb.cancelWhenPressed(lvl3ClimbSequence);
        cancelClimb.cancelWhenPressed(lvl2ClimbSequence);
    }

}
