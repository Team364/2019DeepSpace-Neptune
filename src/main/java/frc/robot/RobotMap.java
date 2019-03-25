package frc.robot;

public class RobotMap {

    // Hardware
    public static final int primaryPCM = 0;
    // DriveTrain
    public static final int leftFrontDrive = 15;// Talon SRX//
    public static final int leftTopDrive = 4;// Victor SPX
    public static final int leftRearDrive = 5;// Victor SPX
    public static final int rightFrontDrive = 12; //CURRENTLY A VICTOR; On Comp: Talon SPX//--figgly--12--was 3
    public static final int rightTopDrive = 6;// 10 //Victor SPX//
    public static final int rightRearDrive = 3;// Victor SPX
    public static final int shifterPort1 = 3;// Primary PCM
    public static final int shifterPort2 = 4;// Primary PCM
    // Lift
    public static final int topLift = 7;// Talon SRX -- Master
    public static final int bottomLift = 13;// Talon SRX -- Gyro
    public static final int lowerLiftLimitSwitch = 3;// Digital Input
    public static final int upperLiftLimitSwitch = 2;// Digital Input
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
    public static final int armLimitSwitch = 1;// Digital Input
    // Climb
    public static final int levitator = 2;// TalonSRX
    public static final int climbDriveMotor = 21;//VictorSPX
    public static final int forearms = 20;//VictorSPX


    // Software
    // Drive
    public static final boolean rightDriveReverse = false;
    public static final boolean rightDriveReverseEncoder = false;
    public static final boolean leftDriveReverse = false;
    public static final boolean leftDriveReverseEncoder = false;
    public static final double driveNominalOutputForward = 0;
    public static final double driveNominalOutputReverse = 0;
    public static final double drivePeakOutputForward = 1;
    public static final double drivePeakOutputReverse = -1;
    public static final int driveCruiseVelocity = 20000;
    public static final int driveAcceleration = 8000;
    // Drive PID
    public static final double drivePgain = 0.2;
    public static final double driveIgain = 0.0;
    public static final double driveDgain = 0.0;
    public static final double driveFgain = 0.2;
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

    public static final int l1Hcam = 100;
    public static final int l1Ccam = 200;
    public static final int l2Hcam = 600;
    public static final int l2Ccam = 800;
    public static final int l3Hcam = 900;
    public static final int l3Ccam = 900;
    public static final int fCam = 400;
    // Lift PID
    public static final double liftPgain = 0.3;
    public static final double liftIgain = 0.0;
    public static final double liftDgain = 0.0;
    public static final double liftFgain = 0.2;
    // Closed Loop Targets
    public static final double liftLowH = 15500;// Level 1 for Hatch
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
    public static final int TimeoutMs = 30;

}
