package frc.robot.commands.teleop.Subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.auto.drive.*;

/**
 * Auto file - Objective - DriveStraight
 */
public class TeleopTurn180 extends CommandGroup {
    /**
     * Turns Robot 180 degrees
     */
    public TeleopTurn180() {
       
        addSequential(new TurnToHeading(175)); //1
        addSequential(new StopDriveMotors());
    
    }
}