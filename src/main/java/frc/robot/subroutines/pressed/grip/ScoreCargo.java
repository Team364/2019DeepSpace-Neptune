package frc.robot.subroutines.pressed.grip;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.grip.*;
import frc.robot.util.States;

/**
 * Subroutine to be run in teleop on button press
 * <p>Robot turns approximately 180 degrees
 */
public class ScoreCargo extends CommandGroup {
    /**
     * Uses Object State to determine which subroutine to run to score
     */
    //TODO:Move the logic into a command. Introduce timed delays with a loop counter
    public ScoreCargo() {

        //Score Cargo Subroutine
        addSequential(new OuttakeCargo());
        addSequential(new IntakePassive());
        States.actionState = States.ActionStates.SCORE_ACT;

    
    }
}