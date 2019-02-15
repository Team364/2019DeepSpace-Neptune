package frc.robot.util;

public class RobotMap {

    //Hardware
    /**This is for the soleniods corresponding to the
     * intake, levers, and shifters */
    public static final int primaryPCM = 0;
    /**This is for the solenoids corresponding to the
     * climb apparatus*/
    public static final int secondaryPCM = 1;
    //DriveTrain
    public static final int leftFrontDrive = 2;//Victor SPX
    public static final int leftTopDrive = 3;//Talon SRX
    public static final int leftRearDrive = 6;//Victor SPX
    public static final int rightFrontDrive = 4; //Victor SPX
    public static final int rightTopDrive = 5;//10 //Talon SRX
    public static final int rightRearDrive = 1;//Victor SPX
    public static final int shifterPort1 = 1;//Primary PCM
    public static final int shifterPort2 = 2;//Primary PCM
    //Lift
    public static final int rightLift = 1;//Talon SRX
    public static final int leftLift = 2;//Talon SRX
    public static final int lowerLiftLimitSwitch = 1;//Digital Input
    public static final int upperLiftLimitSwitch = 3;//Digital Input
    //Grip
    public static final int rightClaw = 4;//Talon SRX
    public static final int leftClaw = 5;//Talon SRX
    public static final int intakePort1 = 3;//Primary PCM
    public static final int intakePort2 = 4;//Primary PCM
    public static final int leverPort1 = 0;//Primary PCM
    public static final int leverPort2 = 7;//Primary PCM
    public static final int ballLimitSwitch = 4;//Digital Input
    //Arm
    public static final int arm = 6; //Talon SRX
    public static final int armLimitSwitch = 2;//Digital Input
    //Climb
    public static final int climbPort1 = 2;//Back
    public static final int climbPort2 = 3;//Back
    public static final int climbPort3 = 4;//Front
    public static final int climbPort4 = 5;//Front

    //Software
     //Drive
    public static final double driveNominalOutputForward = 0;
    public static final double driveNominalOutputReverse = 0;
    public static final double drivePeakOutputForward = 1;
    public static final double drivePeakOutputReverse = -1;
    public static final int driveCruiseVelocity = 20000;
    public static final int driveAcceleration = 8000;
    public static final double driveDampen = 0.5;
    //Lift
     //TalonBase Config
    public static final double liftNominalOutputForward = 0;
    public static final double liftNominalOutputReverse = 0;
    public static final double liftPeakOutputForward = 1;
    public static final double liftPeakOutputReverse = -1;
    public static final int liftCruiseVelocity = 20000;
    public static final int liftAcceleration = 8000;
    public static final boolean liftBounded = true;
    public static final double liftLowerBound = 0;
    public static final double liftUpperBound = 10000;
    public static final double liftDampen = 0.5;
     //Open Loop Config
    public static final int liftAxis = 0;
    public static final double liftDeadband = 0.1;
    //Arm
     //TalonBase Config
    public static final double armNominalOutputForward = 0;
    public static final double armNominalOutputReverse = 0;
    public static final double armPeakOutputForward = 1;
    public static final double armPeakOutputReverse = -1;
    public static final int armCruiseVelocity = 20000;
    public static final int armAcceleration = 8000;
    public static final boolean armBounded = true;
    public static final double armLowerBound = 0;
    public static final double armUpperBound = 3000;
    public static final double armDampen = 0.8;
     //Open Loop Config
    public static final int armAxis = 5;
    public static final double armDeadBand = 0.1;
    //Intake
    public static final double intakeDampen = 0.67;

    //NavX
    public static final double navXPterm = 0.0005;
    public static final double navXIterm = 0.1;
    public static final double navXDterm = 50;
    public static final double navXFterm = 0;
    

    

}
