package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.util.prefabs.commands.*;
import frc.robot.Robot;
/**
 * 
 */
public class ClimbRetract extends CommandGroup {
    /**
     * 
     */
    public ClimbRetract() {
        addSequential(new SetPiston(Robot.superStructure.back, 0));
    }
}