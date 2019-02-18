package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

/**
 * 
 */
public class Retract extends CommandGroup {
    /**
     */
    public Retract() {
        addSequential(new RotateToAngle(4));
    }
}