package frc.robot.subroutines.pressed.grip;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.grip.*;

/**
 * Subroutine to be run in teleop on button press
 */
public class IntakeObject extends CommandGroup {
    /**
     * Uses Object State to determine which subroutine to run to intake
     */
    public IntakeObject() {
        if(Robot.objState == Robot.ObjectStates.HATCH_OBJ){
            //Score Hatch Subroutine
            addSequential(new OpenHatchMechanism());
        }else if(Robot.objState == Robot.ObjectStates.CARGO_OBJ){
            //Score Cargo Subroutine
            addSequential(new IntakeCargo());
            addSequential(new CloseClaw());
        }

    
    }
}