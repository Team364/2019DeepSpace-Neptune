package frc.robot.subroutines.pressed.grip;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.States;
import frc.robot.commands.grip.*;

/**
 * Subroutine to be run in teleop on button press
 */
public class IntakeHatch extends CommandGroup {
    /**
     * Uses Object State to determine which subroutine to run to intake
     */
    public IntakeHatch() {
 
        addSequential(new OpenLever());
       
    
    }
}