package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

/**
 * Subroutine to be run in teleop on button press
 * <p>Robot turns approximately 180 degrees
 * WORKS
 */
public class Elevate extends CommandGroup {
    
    /**
     * <p> Lift Heights:
     * <p>1: low on rocket, scoring hatches on rocket level 1 and Cargo Ship
     * <p>2: middle on rocket
     * <p>3: high on rocket
     * <p>4: cargo height for scoring on Cargo Ship
     * <p> Arm Position Set:
     * <p>1: intakeCargo Position for intaking Cargo
     * <p>2: perpendicular to Ground Position for scoring Hatches 
     * <p>3: scoreOnHigh for scoring on Level 3 of rocket
     * <p>4: startConfig used for retract
     * @param Position
     */
    public Elevate(int Position) {
        addParallel(new RotateToAngle(Position));
        //addSequential(new ElevateToPosition(Position));

    
    }
}