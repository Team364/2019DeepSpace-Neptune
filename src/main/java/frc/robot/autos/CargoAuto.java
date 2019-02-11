package frc.robot.autos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.drive.*;

/**
 * Auto file - Objective - Place 2 hatches on Cargo Ship
 */
public class CargoAuto extends CommandGroup {
    /**
     *Drive to Cargo Ship; Place Hatch; Grab another hatch; Place hatch
     *<p>DeadReckoned
     *<p>Has not been tested on practice feild
     *<p>Distances must be changed
     */
    public CargoAuto() {
        //TODO: Use encoders to ensure precision
        //Currently this is running on raw power and time
        // //This is known as dead reckoning. It is not as accuarte as a closed loop
        // addSequential(new DriveStraightForPower(0.6, 1.5)); //1
        // addSequential(new StopDriveMotors());
        // addSequential(new TurnToHeading(-30));
        // addSequential(new StopDriveMotors());
        // addSequential(new WaitCommand(0.2));

        // addSequential(new DriveStraightForPower(0.6, 1)); //1
        // addSequential(new StopDriveMotors());
        // addSequential(new TurnToHeading(30));
        // addSequential(new StopDriveMotors());
        // addSequential(new WaitCommand(0.2));

        // addSequential(new DriveStraightForPower(0.3, 1));
        // addSequential(new StopDriveMotors());
        // addSequential(new DriveStraightForPower(-0.3, 1));
        // addSequential(new StopDriveMotors());

        // addSequential(new TurnToHeading(120));
        // addSequential(new StopDriveMotors());
        // addSequential(new DriveStraightForPower(0.6, 2.5));
        // addSequential(new StopDriveMotors());
        // addSequential(new TurnToHeading(45));
        // addSequential(new DriveStraightForPower(0.3, 2));
        // addSequential(new StopDriveMotors());
        // addSequential(new DriveStraightForPower(-0.3, 2));
        // addSequential(new StopDriveMotors());

        // addSequential(new TurnToHeading(150));
        // addSequential(new StopDriveMotors());
        // addSequential(new DriveStraightForPower(0.7, 2.5));
        // addSequential(new StopDriveMotors());
        // addSequential(new WaitCommand(0.5));
        // addSequential(new TurnToHeading(-90));
        // addSequential(new StopDriveMotors());
       
        // addSequential(new DriveStraightForPower(0.3, 2));
        // addSequential(new StopDriveMotors());
        // addSequential(new DriveStraightForPower(-0.3, 2));
        // addSequential(new StopDriveMotors());
        
        // addSequential(new WaitCommand(50));
    
    }
}