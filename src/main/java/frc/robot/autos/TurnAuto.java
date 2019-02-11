package frc.robot.autos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.drive.*;

/**
 * Auto file - Objective - DriveStraight
 */
public class TurnAuto extends CommandGroup {
    /**
     *Turn Testing
     *<p>1:Drive Forward
     *<p>2:Turn Left 90 degrees
     *<p>3:Drive Forward
     *<p>4:Turn Right 90 degrees
     *<p>5:Wait for end of Auto Period
     */
    public TurnAuto() {

        // //Drive Forward
        // addSequential(new DriveForPower(0.6, 2)); //1
        // //Turn Left 90 Degrees
        // addSequential(new TurnToHeading(-90));//2
        // addSequential(new WaitCommand(0.2));

        // //Drive Forward
        // addSequential(new DriveForPower(0.6, 2));//3
        // //Turn Right 90 Degrees
        // addSequential(new TurnToHeading(90));//4
        // addSequential(new WaitCommand(0.2));

        // addSequential(new WaitCommand(30));//5

    
    }
}