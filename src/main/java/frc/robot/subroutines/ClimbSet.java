package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.util.prefabs.commands.*;
import frc.robot.Robot;
import frc.robot.commands.*;
/**
 * 
 */
public class ClimbSet extends CommandGroup {
    /**
     * 
     */
    public ClimbSet() {

        // addSequential(new SetPiston(Robot.superStructure.back, 1));
        addSequential(new ClimbElevate());
        addSequential(new SetPiston(Robot.superStructure.front, 1));
    }
}