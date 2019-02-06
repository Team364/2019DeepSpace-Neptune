package frc.robot.subroutines.pressed.grip;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.States;
import frc.robot.commands.grip.*;

/**
 * Subroutine to be run in teleop on button press
 * <p>Robot turns approximately 180 degrees
 */
public class ScoreObject extends CommandGroup {
    /**
     * Uses Object State to determine which subroutine to run to score
     */
    public ScoreObject() {
        if(States.objState == States.ObjectStates.HATCH_OBJ){
            //Score Hatch Subroutine
            addSequential(new CloseHatchMechanism());
            addSequential(new ReturnToDefault());
        }else if(States.objState == States.ObjectStates.CARGO_OBJ){
            //Score Cube Subroutine
            addSequential(new OuttakeCargo());
            addSequential(new ReturnToDefault());
        }

    
    }
}