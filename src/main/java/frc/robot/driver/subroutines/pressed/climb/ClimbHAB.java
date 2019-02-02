package frc.robot.driver.subroutines.pressed.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.driver.commands.climb.*;
import frc.robot.driver.commands.lift.KeepPitch;

/**
 * Subroutine to be run in teleop on button press
 * <p>Robot turns approximately 180 degrees
 */
public class ClimbHAB extends CommandGroup {
    /**
     * Climbs onto HAB lvl 3
     * <p>1: Extends lift wheels 
     * <p>2: Keeps the robot level
     * <p>3: Climbs
     */
    public ClimbHAB() {

        addSequential(new OpenWheels()); //1
        addParallel(new KeepPitch()); //2
        addSequential(new Climb()); //3

    
    }
}