package frc.robot.util;

public class RobotMap {

    //Drive Motor Controllers
    //Victor SPX
    public static final int leftFrontDrive = 2;
    //Talon SRX
    public static final int leftTopDrive = 3;
    //Victor SPX
    public static final int leftRearDrive = 6;

    //Victor SPX
    public static final int rightFrontDrive = 4;
    //Talon SRX
    public static final int rightTopDrive = 10;
    //Victor SPX
    public static final int rightRearDrive = 1;

    //This talon was used as a host for the pigeon IMU
    // public static final int extraTalon = 5;

    //Must change depending on what is on the robot
    //Lift
    //Talon SRX
    public static final int rightLift = 1;
    //Talon SRX
    public static final int leftLift = 2;
    //Claw
    //Talon SRX
    public static final int rightClaw = 4;
    //Talon SRX
    public static final int leftClaw = 5;
    //Arm
    //Talon SRX
    public static final int arm = 6;

    //TODO: Set these according to how the robot is wired
    public static final int shifterPort1 = 0;
    public static final int shifterPort2 = 7;

    public static final int intakePort1 = 2;
    public static final int intakePort2 = 3;
    
    public static final int leverPort1 = 4;
    public static final int leverPort2 = 5;

    //Back Pistons
    public static final int climbPort1 = 4;
    public static final int climbPort2 = 5;
    //Wheel Pistons
    public static final int climbPort3 = 6;
    public static final int climbPort4 = 7;

    public static final int ballLimitSwitch = 3;
    public static final int armLimitSwitch = 2;
    public static final int lowerLiftLimitSwitch = 1;
    public static final int upperLiftLimitSwitch = 4;
    

}
