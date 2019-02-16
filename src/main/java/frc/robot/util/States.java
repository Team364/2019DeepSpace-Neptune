package frc.robot.util;

public class States{

    /** State for whether robot is in hatch mode or cargo mode*/
    public static enum ObjectStates {
        CARGO_OBJ,
        HATCH_OBJ
    }
    /**State tracks what action the robot is taking
    Intake is for intaking objects
    Ferry is when the robot is carrying an object
    Score is when the robot is outtaking an object
    Passive is for when the robot is not in possession of an object and is not actively seeking to obtain one
    */
    public static enum ActionStates {
        INTAKE_ACT,
        FERRY_ACT,
        SCORE_ACT,
        PASSIVE
    }
    /** If the robot is is closed loop then the users cannot directly affect arm or
    elevator movement. This state refers to scoring mechanisms directly(i.e. arm, elevator)
    */
    public static enum LoopStates{
        OPEN_LOOP,
        CLOSED_LOOP
    }
    /**This is essentially intended to be used for tracking whether or not vision is being used to navigate
     * but auto drive mode will be included here too */  
    public static enum DriveStates{
        OPEN_LOOP,
        VISION,
        AUTO,
    }
    public static enum DriveMotionStates{
        MOVING,
        NOT_MOVING
    }
    /**
     * State tracks where the user is attempting to score based on button press and object state
     */
    public static enum ScoreStates{
        ROCKET_LOW,
        ROCKET_MED,
        ROCKET_HIGH,
        CARGO_LOW,
        CARGO_HIGH,
        NONE
    }

    /**
     * State tracks where in climb robot is
     */
    public static enum ClimbStates{
        START_CLIMB,
        CLIMBING,
        FINISHED_CLIMBING,
        NOT_CLIMBING
    }
    public static ObjectStates objState = ObjectStates.CARGO_OBJ;
    public static ActionStates actionState = ActionStates.PASSIVE;
    public static LoopStates loopState = LoopStates.OPEN_LOOP;
    public static DriveStates driveState = DriveStates.OPEN_LOOP;
    public static DriveMotionStates driveMotionState = DriveMotionStates.NOT_MOVING;
    public static ScoreStates scoreState = ScoreStates.NONE;
    public static ClimbStates climbState = ClimbStates.NOT_CLIMBING;
}


