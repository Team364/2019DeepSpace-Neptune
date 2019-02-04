package frc.robot.subroutines.pressed.drive;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.drive.*;

/**
 * Subroutine to be run in teleop on button press
 * <p>Robot turns approximately 180 degrees
 */
public class TeleopTurn180 extends CommandGroup {
    /**
     * Turns Robot 180 degrees
     * <p>1: Turn
     * <p>2: Stop Drive Motors
     */
    public TeleopTurn180() {
        //Turn
        addSequential(new TurnToHeading(175)); //1
        //Stop Motors
        addSequential(new StopDriveMotors());//2
    
    }
}