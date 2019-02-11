package frc.robot.subroutines.pressed.grip;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.States;
import frc.robot.commands.grip.*;

/**
 * Subroutine to be run in teleop on button press
 * <p>Robot turns approximately 180 degrees
 */
public class ScoreHatch extends CommandGroup {
    /**
     * Uses Object State to determine which subroutine to run to score
     */
    //TODO:Move the logic into a command. Introduce timed delays with a loop counter
    public ScoreHatch() {
            //Score Hatch Subroutine
            addSequential(new CloseLever());
            addSequential(new IntakePassive());
    }
}