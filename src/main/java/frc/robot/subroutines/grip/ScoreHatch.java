package frc.robot.subroutines.grip;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.*;
import frc.robot.util.States;

/**
 * Subroutine to be run in teleop on button press
 * <p>Robot turns approximately 180 degrees
 */
public class ScoreHatch extends CommandGroup {

    public ScoreHatch() {
            //Score Hatch Subroutine
            addSequential(new SetPiston(Robot.superStructure.lever, 1));
            addSequential(new IntakePassive());
            States.actionState = States.ActionStates.SCORE_ACT;
    }
}