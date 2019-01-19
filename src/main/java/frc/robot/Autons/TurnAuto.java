package frc.robot.Autons;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.auto.drive.*;

/**
 * Auto file - Objective - DriveStraight
 */
public class TurnAuto extends CommandGroup {
    /**
     *Turn Testing
     */
    public TurnAuto() {
       
        addSequential(new DriveForPower(0.6, 2)); //1
        addSequential(new StopDriveMotors());
        addSequential(new TurnToHeading(-90));
        addSequential(new StopDriveMotors());
        addSequential(new WaitCommand(0.2));

        addSequential(new DriveForPower(0.6, 2)); //1
        addSequential(new StopDriveMotors());
        addSequential(new TurnToHeading(90));
        addSequential(new StopDriveMotors());
        addSequential(new WaitCommand(0.2));

        addSequential(new WaitCommand(30));

    
    }
}