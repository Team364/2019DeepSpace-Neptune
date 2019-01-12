package frc.robot.Autons;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.auto.drive.*;

/**
 * Auto file - Objective - DriveStraight
 */
public class Auto3 extends CommandGroup {
    /**
     * Objective - Cross the line
     *<p>1 Drive Foward
     */
    public Auto3() {
       
        addSequential(new DriveStraightForPower(0.6, 6)); //1
        addSequential(new StopDriveMotors());

        addSequential(new WaitCommand(30));

    
    }
}