package frc.robot;

public class States {

    /** State for whether robot is in hatch mode or cargo mode */
    public static enum ObjectStates {
        CARGO_OBJ, HATCH_OBJ
    }

    /**
     * State tracks what action the robot is taking Intake is for intaking objects
     * Ferry is when the robot is carrying an object Score is when the robot is
     * outtaking an object Passive is for when the robot is not in possession of an
     * object and is not actively seeking to obtain one
     */
    public static enum ActionStates {
        INTAKE_ACT, FERRY_ACT, SCORE_ACT, PASSIVE, SEEK
    }
    
    public static enum LEDstates{
        INTAKE_MODE, HAS_OBJ, CLIMBING, PASSIVE
    }

    /**
     * This is essentially intended to be used for tracking whether or not vision is
     * being used to navigate but auto drive mode will be included here too
     */
    public static enum DriveStates {
        OPEN_LOOP, VISION, AUTO,
    }

    /**
     * State tracks where the user is attempting to score based on button press and
     * object state
     */
    public static enum ScoreStates {
        ROCKET_LOW, ROCKET_MED, ROCKET_HIGH, CARGO_LOW, CARGO_HIGH, NONE
    }

    /** State Tracks Height of the Lift */
    public static enum LiftZones {
        UPPER_DANGER, SAFE, LOWER_DANGER
    }

    public static ObjectStates objState = ObjectStates.HATCH_OBJ;
    public static ActionStates actionState = ActionStates.PASSIVE;
    public static DriveStates driveState = DriveStates.OPEN_LOOP;
    public static ScoreStates scoreState = ScoreStates.NONE;
    public static LiftZones liftZone = LiftZones.LOWER_DANGER;
    public static LEDstates led = LEDstates.PASSIVE;
}
