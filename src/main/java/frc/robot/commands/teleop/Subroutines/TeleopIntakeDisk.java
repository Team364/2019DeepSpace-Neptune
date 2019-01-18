package frc.robot.commands.teleop.Subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.auto.drive.*;

/**
 * Auto file - Objective - DriveStraight
 */
public class TeleopIntakeDisk extends CommandGroup {
    /**
     * Turns Robot 180 degrees
     */
    public TeleopIntakeDisk() {
    
        addSequential(new TurnToDisk());
        addSequential(new StopDriveMotors());
    }
}