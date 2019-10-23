package frc.robot;

public class RobotMap {

    // Hardware
    public static final int primaryPCM = 0;


    //Gyro reset button
    public static final int RESETGYRO = 4;

    //Mods reset button
    public static final int RESETMODS = 3;


    //Gyro Set Points
    public static final double[] CARGOGYROSET = 
    {
        90,
        270,
        0
    };
    //Gyro Set Points
    public static final double[] ROCKETGYROSET = 
    {
        32,
        328,
        90,
        270,
        148,
        212

    };
    //Gyro Set Points
    public static final double[] INTAKEGYROSET = 
    {
        180
    };

    //Lime Aim Buttons
    public static final int LIMEBUTTONCARGO = 5;
    public static final int LIMEBUTTONROCKET = 6;
    public static final int LIMEBUTTONINTAKE = 1;



    //hardware
    public static final int TRACKWIDTH = 21;
    public static final int WHEELBASE = 26;
    public static final int WHEELDIAMETER = 3;
    
    public static final int FLANGLE = 16;//FL 2-> BR 8/////////////16
    public static final int FLENCODER = 1;
    public static final int FLDRIVE = 36;//FL 1-> BR 7/////////////36
    public static final int FRANGLE = 32;//FR 6-> BL 12////////////32
    public static final int FRENCODER = 0;
    public static final int FRDRIVE = 12;//FR 5-> BL 3/////////////12
    public static final int BRANGLE = 33;//BR 8-> FL 2
    public static final int BRENCODER = 3;
    public static final int BRDRIVE = 15;//BR 7-> FL 1
    public static final int BLANGLE = 39;//was 4 //BL 12-> FR
    public static final int BLENCODER = 2;
    public static final int BLDRIVE = 37;//BL 3-> FR

    public static final int PIGEON = 13;

    //Offsets 
    /**Front Right */
    public static final int MOD0OFFSET = 0;
    /**Front Left */
    public static final int MOD1OFFSET = 0;
    /**Back Left */
    public static final int MOD2OFFSET = 0;
    /**Back Right */
    public static final int MOD3OFFSET = 0;

    //constants
    public static final double STICKDEADBAND = 0.2;
    public static final double ENCODERTICKS = 4096.0;
    public static final double OFFSETTOSTRAIGHT = 180;
    //public static final double ANGLE_TICKS_PER_RADIAN = ENCODERTICKS / (2.0 * Math.PI);


    public static final int SLOTIDX = 0;
    public static final int SWERVETIMEOUT = 20;

    public static final double ANGLEP = 2.5;//20
    public static final double ANGLEI = 0.0;//0.001
    public static final double ANGLED = 0;//130 //200

    public static final int ANGLECONTINUOUSCURRENTLIMIT = 30;
    public static final int ANGLEPEAKCURRENT = 30;
    public static final int ANGLEPEAKCURRENTDURATION = 100;
    public static final boolean ANGLEENABLECURRENTLIMIT = true;

    public static final int DRIVECONTINUOUSCURRENTLIMIT = 30;
    public static final int DRIVEPEAKCURRENT = 30;
    public static final int DRIVEPEAKCURRENTDURATION = 100;
    public static final boolean DRIVEENABLECURRENTLIMIT = true;

    // Lift
    public static final int topLift = 7;// Talon SRX -- Master
    public static final int bottomLift = 13;// Talon SRX -- Gyro
    public static final int servoCamera = 0;//PWM
    // Grip
    public static final int rightClaw = 1;// Victor SPX
    public static final int leftClaw = 7;// Victor SPX
    public static final int intakePort1 = 1;// Primary PCM//1
    public static final int intakePort2 = 6;// Primary PCM//6
    public static final int leverPort1 = 0;// Primary PCM//0
    public static final int leverPort2 = 7;// Primary PCM//7
    public static final int intakeLimitSwitch = 0;// Digital Input
    // Arm
    public static final int arm = 6; // Talon SRX
    // Climb
    public static final int levitator = 2;// TalonSRX
    public static final int climbDriveMotor = 20;//VictorSPX
    public static final int forearms = 4;//VictorSPX


    // Software

    // Lift
    // TalonBase Config
    public static final boolean liftReverse = false;//was false
    public static final boolean liftReverseEncoder = false;
    public static final int liftCurrentCeiling = 39;
    public static final int liftCurrentCeilingDuration = 1;
    public static final double liftNominalOutputForward = 0;
    public static final double liftNominalOutputReverse = 0;
    public static final double liftPeakOutputForward = 1;
    public static final double liftPeakOutputReverse = -0.75;
    public static final int liftCruiseVelocity = 10000;
    public static final int liftCruiseVelocityClimb = 3500;
    public static final int liftAcceleration = 10000;
    public static final int liftAccelerationClimb = 1000;
    public static final double liftLowerBound = -100;
    public static final double liftUpperBound = 135000;
    
    public static final int l1Hcam = 1900;//300 *with old servo*
    public static final int l1Ccam = 1900;//300
    public static final int l2Hcam = 1600;//650
    public static final int l2Ccam = 1500;//850
    public static final int l3Hcam = 1400;//1000
    public static final int l3Ccam = 1400;//1000
    public static final int fCam = 1800;

    // Lift PID
    public static final double liftPgain = 0.3;
    public static final double liftIgain = 0.0;
    public static final double liftDgain = 0.0;
    public static final double liftFgain = 0.2;
    // Closed Loop Targets
    public static final double liftLowH = 13000;// Level 1 for Hatch
    public static final double liftMedH = 77000;// Level 2 Rocket for Hatch
    public static final double liftHighH = 132000;// Level 3 Rocket for Hatch
    public static final double liftLowC = 35000;// Level 1 Rocket Cargo
    public static final double liftMedC = 92000;// Level 2 Rocket Cargo
    public static final double liftHighC = 125000;// Level 3 Rocket Cargo
    public static final double liftCargoC = 75000;// Scoring Cargo in Cargo Ship
    public static final double liftIntake = 12000;// Intaking Cargo
    public static final double liftStartConfig = 1000;// Starting Config for Lift
    // Arm
    // TalonBase Config
    public static final boolean armReverse = true;
    public static final boolean armReverseEncoder = true;
    public static final double armNominalOutputForward = 0;
    public static final double armNominalOutputReverse = 0;
    public static final double armPeakOutputForward = 1;
    public static final double armPeakOutputReverse = -1;
    public static final int armCruiseVelocity = 2000;
    public static final int armAcceleration = 800;
    public static final double armLowerBound = -25;
    public static final double armUpperBound = 4300;
    // Arm PID
    public static final double armPgain = 0.8;
    public static final double armIgain = 0.0;
    public static final double armDgain = 0.0;
    public static final double armFgain = 0.0;
    // Closed Loop Targets
    public static final double armIntakeCargo = 4450; //4400
    public static final double armPerpindicularToGround = 3450;//3300
    public static final double armStartConfig = 100;//100
    public static final double armScoreOnHigh = 2550;//2400
    // Intake
    public static final double intakeNominalOutputForward = 0;
    public static final double intakeNominalOutputReverse = 0;
    public static final double intakePeakOutputForward = 1;
    public static final double intakePeakOutputReverse = -1;
    // Drop Wheels
    public static final double dropWheelsNominalOutputForward = 0;
    public static final double dropWheelsNominalOutputReverse = 0;
    public static final double dropWheelsPeakOutputForward = 0.5;
    public static final double dropWheelsPeakOutputReverse = -0.5;
    //Climber
    public static final boolean levitatorSensorPhase = false;
    public static final int levitatorCruiseVelocity = 950;
    public static final int levitatorAcceleration = 1800;
    // Lift PID
    public static final double levitatorPgain = 3;
    public static final double levitatorIgain = 0.0;
    public static final double levitatorDgain = 0.0;
    public static final double levitatorFgain = 0.0;
    //Climb Setpoints
    public static final double lvl3Climb = 26000;//27000
    public static final double lvl2Climb = 11000;
    public static final double intermediateClimb = 18000;
    // PID constants
    public static final int SlotIdx = 0;
    public static final int PIDLoopIdx = 0;
    public static final int TimeoutMs = 20;

}
