package frc.robot.autos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.commands.*;

/**
 * Auto file - Objective - DriveStraight
 */
public class StraightAuto extends CommandGroup {
    /**
     * Drive Straight
     * <p>1:Drive while NavX gryo corrects path
     * <p>2:Wait for end of Auto Period
     */
    public StraightAuto() {
        
        //Drive while NavX Gyro corrects path
        // addSequential(new DriveStraightForPower(0.6, 6)); //1
        // //Wait for end of Auto Period
        // addSequential(new WaitCommand(30));//2

    
    }
}