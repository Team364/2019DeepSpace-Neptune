package frc.robot;

public class RobotMap {

    //Hardware
    /**This is for the soleniods corresponding to the
     * intake, levers, and shifters */
    public static final int primaryPCM = 0;
    /**This is for the solenoids corresponding to the
     * climb apparatus*/
    public static final int secondaryPCM = 1;
    //DriveTrain
    public static final int leftFrontDrive = 15;//Talon SRX//
    public static final int leftTopDrive = 4;//Victor SPX
    public static final int leftRearDrive = 5;//Victor SPX
    public static final int rightFrontDrive = 12; //Talon SPX//
    public static final int rightTopDrive = 6;//10 //Victor SPX//
    public static final int rightRearDrive = 3;//Victor SPX
    public static final int shifterPort1 = 3;//Primary PCM
    public static final int shifterPort2 = 4;//Primary PCM
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
     //Closed Loop Targets
    public static final double liftLowH = 5000;//Level 1 for Hatch
    public static final double liftMedH = 10000;//Level 2 Rocket for Hatch
    public static final double liftHighH = 15000;//Level 3 Rocket for Hatch
    public static final double liftLowC = 7500;//Level 1 Rocket Cargo
    public static final double liftMedC = 12500;//Level 2 Rocket Cargo
    public static final double liftHighC = 17500;//Level 3 Rocket Cargo
    public static final double liftCargoC = 11000;//Scoring Cargo in Cargo Ship
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
     //Closed Loop Targets
     public static final double armIntakeCargo = 1137;/*100 degrees*/
     public static final double armPerpindicularToGround = 2275;/*200 degrees*/
     public static final double armStartConfig = 0;/*0 degrees*/
     public static final double armScoreOnHigh = 3413;/*300 degrees*/
    //1706 /*150 degrees*/;
    //2844 /*250 degrees*/
    //3413/*300 degrees*/;
    //3981 /*350 degrees*/;
    //Intake
    public static final double intakeDampen = 0.67;

    //NavX
    public static final double navXPterm = 0.0005;
    public static final double navXIterm = 0.1;
    public static final double navXDterm = 50;
    public static final double navXFterm = 0;
    

    

}
