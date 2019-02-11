package frc.robot.subroutines.grip;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.*;
import frc.robot.util.States;

/**
 * Subroutine to be run in teleop on button press
 * <p>Robot turns approximately 180 degrees
 */
public class ScoreCargo extends CommandGroup {

    public ScoreCargo() {

        //Score Cargo Subroutine
        addSequential(new runIntake(-0.75, false));
        addSequential(new IntakePassive());
        States.actionState = States.ActionStates.SCORE_ACT;

    
    }
}