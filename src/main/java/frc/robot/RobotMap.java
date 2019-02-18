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
    public static final int rightLift = 7;//Talon SRX -- Master
    public static final int leftLift = 13;//Talon SRX -- Gyro
    public static final int lowerLiftLimitSwitch = 3;//Digital Input
    public static final int upperLiftLimitSwitch = 2;//Digital Input
    //Grip
    public static final int rightClaw = 1;//Victor SPX
    public static final int leftClaw = 7;//Victor SPX
    public static final int intakePort1 = 1;//Primary PCM
    public static final int intakePort2 = 6;//Primary PCM
    public static final int leverPort1 = 0;//Primary PCM
    public static final int leverPort2 = 7;//Primary PCM
    public static final int ballLimitSwitch = 1;//Digital Input
    //Arm
    public static final int arm = 6; //Talon SRX
    public static final int armLimitSwitch = 0;//Digital Input
    //Climb
    public static final int dropWheels = 2;//VictorSPX
    public static final int climbPort1 = 2;//Back
    public static final int climbPort2 = 5;//Back
    public static final int climbPort3 = 0;//Front
    public static final int climbPort4 = 7;//Front

    //Software
     //Drive
    public static final double driveNominalOutputForward = 0;
    public static final double driveNominalOutputReverse = 0;
    public static final double drivePeakOutputForward = 0.8;
    public static final double drivePeakOutputReverse = -0.8;
    public static final int driveCruiseVelocity = 20000;
    public static final int driveAcceleration = 8000;
    //Lift
     //TalonBase Config
    public static final double liftNominalOutputForward = 0;
    public static final double liftNominalOutputReverse = 0;
    public static final double liftPeakOutputForward = 0.5;
    public static final double liftPeakOutputReverse = -0.5;
    public static final int liftCruiseVelocity = 10000;
    public static final int liftAcceleration = 4000;
    public static final boolean liftBounded = true;
    public static final double liftLowerBound = 0;
    public static final double liftUpperBound = -128000;
     //Open Loop Config
    public static final int liftAxis = 1;
    public static final double liftDeadband = 0.1;
     //Closed Loop Targets
    public static final double liftLowH = -17000;//Level 1 for Hatch
    public static final double liftMedH = -74000;//Level 2 Rocket for Hatch
    public static final double liftHighH = -110000;//Level 3 Rocket for Hatch
    public static final double liftLowC = -30000;//Level 1 Rocket Cargo
    public static final double liftMedC = -70000;//Level 2 Rocket Cargo
    public static final double liftHighC = -120000;//Level 3 Rocket Cargo
    public static final double liftCargoC = -80000;//Scoring Cargo in Cargo Ship
    public static final double liftIntake = -10000;//Intaking Cargo
    public static final double liftStartConfig = -1000;//Starting Config for Lift
    //Arm
     //TalonBase Config
    public static final double armNominalOutputForward = 0;
    public static final double armNominalOutputReverse = 0;
    public static final double armPeakOutputForward = 0.1;
    public static final double armPeakOutputReverse = -0.35;
    public static final int armCruiseVelocity = 2000;
    public static final int armAcceleration = 800;
    public static final boolean armBounded = true;
    public static final double armLowerBound = -3000;
    public static final double armUpperBound = 0;
     //Open Loop Config
    public static final int armAxis = 5;
    public static final double armDeadBand = 0.1;
     //Closed Loop Targets
     public static final double armIntakeCargo = -4400;
     public static final double armPerpindicularToGround = -3500;//-3500;
     public static final double armStartConfig = -900;/*0 degrees*/
     public static final double armScoreOnHigh = -1003;/*300 degrees*/
    //1706 /*150 degrees*/;
    //2844 /*250 degrees*/
    //3413/*300 degrees*/;
    //3981 /*350 degrees*/;
    //Intake
    public static final double intakeNominalOutputForward = 0;
    public static final double intakeNominalOutputReverse = 0;
    public static final double intakePeakOutputForward = 1;
    public static final double intakePeakOutputReverse = -1;
    //Drop Wheels
    public static final double dropWheelsNominalOutputForward = 0;
    public static final double dropWheelsNominalOutputReverse = 0;
    public static final double dropWheelsPeakOutputForward = 0.5;
    public static final double dropWheelsPeakOutputReverse = -0.5;
    //NavX
    public static final double navXPterm = 0.0005;
    public static final double navXIterm = 0.1;
    public static final double navXDterm = 50;
    public static final double navXFterm = 0;
    

    

}
