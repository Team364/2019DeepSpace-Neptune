package frc.robot.operator.subroutines.pressed.claw;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.operator.commands.claw.*;

/**
 * Subroutine to be run in teleop on button press
 * <p>Robot turns approximately 180 degrees
 */
public class ScoreObject extends CommandGroup {
    /**
     * Uses Object State to determine which subroutine to run to score
     */
    public ScoreObject() {
        if(Robot.objState == Robot.ObjectStates.HATCH_OBJ){
            //Score Hatch Subroutine
            addSequential(new CloseHatchMechanism());
            addSequential(new ReturnToDefault());
        }else if(Robot.objState == Robot.ObjectStates.CARGO_OBJ){
            //Score Cube Subroutine
            addSequential(new OuttakeCargo());
            addSequential(new ReturnToDefault());
        }

    
    }
}