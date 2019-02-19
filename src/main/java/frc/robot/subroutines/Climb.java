package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.util.prefabs.commands.*;
import frc.robot.Robot;
import frc.robot.commands.*;
/**
 * 
 */
public class Climb extends CommandGroup {
    /**
     * 
     */
    public Climb() {

        //addSequential(new SetPiston(Robot.superStructure.back, 1));
    }
}