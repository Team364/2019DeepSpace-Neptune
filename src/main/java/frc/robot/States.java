package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class States{

    //TODO: Write the logic for the rest of the states in the project
    //NOT A PRIORITY -- Get robot to run first before testing any of this
    /** State for whether robot is in hatch mode or cargo mode*/
    public static enum ObjectStates {
        CARGO_OBJ,
        HATCH_OBJ
    }
    /**State tracks what action the robot is taking
    Intake is for intaking objects
    Ferry is when the robot is carrying an object
    Score is when the robot is outtaking an object
    No is for when the robot is not in possession of an object and is not actively seeking to obtain one
    The retract arm action will most likely use this. The robot will start in this state
    I am considering making this the state the robot will be in during teleop if the drivers are not
    attempting to take positive control of an object to minimize possible damage to the apparatus*/
    public static enum ActionStates {
        INTAKE_ACT,
        FERRY_ACT,
        SCORE_ACT,
        NO_ACT
    }
    /** State tracks what gear the driveTrain is */
    public static enum ShfitStates{
        HIGH_SHIFT,
        LOW_SHIFT
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
     * State tracks pistons on grip for claw
     */
    public static enum ClawStates{
        CLOSED,
        OPEN
    }
    /**
     * State tracks pistons on grip for lever
     */
    public static enum LeverStates{
        CLOSED,
        OPEN
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
    public static enum ClimbPisonStates{
        CLOSED,
        OPEN
    }
    public static enum ClimbWheelStates{
        CLOSED,
        OPEN
    }
    //Object State is declared and set to cargo
    //TODO: Ensure that these mirror what the robots starting configuration is
    public static ObjectStates objState = ObjectStates.CARGO_OBJ;
    public static ActionStates actionState = ActionStates.NO_ACT;
    public static ShfitStates shiftState = ShfitStates.LOW_SHIFT;
    public static LoopStates loopState = LoopStates.OPEN_LOOP;
    public static DriveStates driveState = DriveStates.OPEN_LOOP;
    public static DriveMotionStates driveMotionState = DriveMotionStates.NOT_MOVING;
    public static ScoreStates scoreState = ScoreStates.NONE;
    public static ClawStates clawState = ClawStates.CLOSED;
    public static LeverStates leverState = LeverStates.CLOSED;
    public static ClimbStates climbState = ClimbStates.NOT_CLIMBING;
    public static ClimbPisonStates climbPisionState = ClimbPisonStates.CLOSED;
    public static ClimbWheelStates climbWheelStates = ClimbWheelStates.CLOSED;

}


