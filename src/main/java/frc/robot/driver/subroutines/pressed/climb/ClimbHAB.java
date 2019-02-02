package frc.robot.driver.subroutines.pressed.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.driver.commands.climb.*;

/**
 * Subroutine to be run in teleop on button press
 * <p>Robot turns approximately 180 degrees
 */
public class ClimbHAB extends CommandGroup {
    /**
     * Turns Robot 180 degrees
     * <p>1: Turn
     * <p>2: Stop Drive Motors
     */
    public ClimbHAB() {

        addSequential(new OpenWheels()); //1


    
    }
}