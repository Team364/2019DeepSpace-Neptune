package frc.robot.Autons;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.auto.drive.*;

/**
 * Auto file - Objective - DriveStraight
 */
public class CargoAuto extends CommandGroup {
    /**
     * Objective - Cross the line
     *<p>1 Drive Foward
     */
    public CargoAuto() {
       
        addSequential(new DriveStraightForPower(0.6, 1.5)); //1
        addSequential(new StopDriveMotors());
        addSequential(new TurnToHeadingS(-30));
        addSequential(new StopDriveMotors());
        addSequential(new WaitCommand(0.2));

        addSequential(new DriveStraightForPower(0.6, 1)); //1
        addSequential(new StopDriveMotors());
        addSequential(new TurnToHeadingS(30));
        addSequential(new StopDriveMotors());
        addSequential(new WaitCommand(0.2));

        addSequential(new DriveStraightForPower(0.3, 1));
        addSequential(new StopDriveMotors());
        addSequential(new DriveStraightForPower(-0.3, 1));
        addSequential(new StopDriveMotors());

        addSequential(new TurnToHeadingL(120));
        addSequential(new StopDriveMotors());
        addSequential(new DriveStraightForPower(0.6, 2.5));
        addSequential(new StopDriveMotors());
        addSequential(new TurnToHeadingS(45));
        addSequential(new DriveStraightForPower(0.3, 2));
        addSequential(new StopDriveMotors());
        addSequential(new DriveStraightForPower(-0.3, 2));
        addSequential(new StopDriveMotors());

        addSequential(new TurnToHeadingL(150));
        addSequential(new StopDriveMotors());
        addSequential(new DriveStraightForPower(0.7, 2.5));
        addSequential(new StopDriveMotors());
        addSequential(new WaitCommand(0.5));
        addSequential(new TurnToHeadingL(-90));
        addSequential(new StopDriveMotors());
       
        addSequential(new DriveStraightForPower(0.3, 2));
        addSequential(new StopDriveMotors());
        addSequential(new DriveStraightForPower(-0.3, 2));
        addSequential(new StopDriveMotors());
        
        addSequential(new WaitCommand(50));
    
    }
}