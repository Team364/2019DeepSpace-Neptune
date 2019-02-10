package frc.robot.subroutines.pressed.arm;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.arm.*;

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