package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.defaultcommands.DriveOpenLoop;
import frc.robot.States;
// import com.ctre.phoenix.motorcontrol.ControlMode;
// // import com.ctre.phoenix.sensors.PigeonIMU;
// import com.kauailabs.navx.frc.AHRS;
// import edu.wpi.first.wpilibj.DoubleSolenoid;
// import edu.wpi.first.wpilibj.SPI;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.util.PIDCalc;
// import frc.robot.RobotMap;


// import com.ctre.phoenix.motorcontrol.can.TalonSRX;
// import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class DriveSystem extends Subsystem {

    //Motor Controller Declarations
    // private TalonSRX leftTop;
    // private TalonSRX rightTop;

    // private VictorSPX leftFront;
    // private VictorSPX leftRear;
    // private VictorSPX rightFront;
    // private VictorSPX rightRear;

    // private TalonSRX extraTalon;
    // private PigeonIMU testPigeon;
    //Shifter
    // private DoubleSolenoid shifter;
    // //Gyro
    // public AHRS navX;
    // //PID Gyro
    // public PIDCalc pidNavX;
    // public double pidOutputNavX;
    // //PID Left
    // public PIDCalc pidLeft;
    // private double pidOutputLeft;
    // //PID Right
    // public PIDCalc pidRight;
    // private double pidOutputRight;
    /**
     * DriveSystem()
     * Constructor for the DriveSystem class
     * Maps all TalonSRX's and configures settings
     * Maps all pistons
     * Creates PID objects
     * Initializes navX
     * Initializes Pathfinder class
     */ 
    public DriveSystem() {
        
        //  // Initialize TalonSRX and VictorSPX objects
        //  leftFront = new VictorSPX(RobotMap.leftFrontDrive);
        //  leftTop = new TalonSRX(RobotMap.leftTopDrive);
        //  leftRear = new VictorSPX(RobotMap.leftRearDrive);
 
        //  rightFront = new VictorSPX(RobotMap.rightFrontDrive);
        //  rightTop = new TalonSRX(RobotMap.rightTopDrive);
        //  rightRear = new VictorSPX(RobotMap.rightRearDrive);
         
        // //  extraTalon = new TalonSRX(RobotMap.extraTalon);
 
        //  // Initialize DoubleSolenoid shifter object
        //  shifter = new DoubleSolenoid(RobotMap.shifterPort1, RobotMap.shifterPort2);

        // //Initialize PigeonIMU
        // //  testPigeon = new PigeonIMU(extraTalon);
        
        //  // Set the front drive motors to follow the rear
        //  leftFront.follow(leftTop);
        //  leftRear.follow(leftTop);
        //  rightFront.follow(rightTop);
        //  rightRear.follow(rightTop);
 
        //  // Config PF on left side
        //  leftRear.config_kP(0, 0.25, 100);
        //  leftRear.config_kF(0, 1, 100);
 
        //  // Config PF on right side
        //  rightRear.config_kP(0, 0.25, 100);
        //  rightRear.config_kF(0, 1, 100);
 
        //  // Init the navX
        //  //DriveSystem Gyro
        //  navX = new AHRS(SPI.Port.kMXP);
        //  //PIDCalc Init
        //  //DriveSystem Gyro PID init
        //  pidNavX = new PIDCalc(0.0005, 0.1, 50, 0, "NavX");
        //  //DriveSystem Left Side init
        //  pidLeft = new PIDCalc(0.0005, 0, 0, 0, "Left");
        //  //DriveSystem Right Side init
        //  pidRight = new PIDCalc(0.0005, 0, 0, 0, "Right");
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveOpenLoop());
    }

    /**
     * tankDrive()
     * Sets manual control of the drivetrain for teleop
     * @param left sets the left drive power
     * @param right sets the right drive power
     */
    private void tankDrive(double left, double right) {
        System.out.println("Left is set to: " + left);
        System.out.println("Right is set to: " + right);
        // leftTop.set(ControlMode.PercentOutput, -left);
        // rightTop.set(ControlMode.PercentOutput, right);
    }
    /**
     * triggerDrive()
     * <p>Sets forward and backward drive of drivetrain to triggers
     * <p>X axis of left Joystick of the controller control turn
     * <p>uses tankDrive method to control the drivetrain
     * @param throttle sets forward and backward values for drivetrain
     * @param steer adds power to left and subtracts power from right to turn
     */
    private void triggerDrive(double throttle, double steer){
        System.out.println("Throttle is: " + throttle);
        System.out.println("Steer is: " + steer);
        // double leftDrive;
        // double rightDrive;
        // leftDrive = throttle + steer;
        // rightDrive = throttle - steer;
        // tankDrive(leftDrive, rightDrive);
    }
    
    public void openLoop(double param1, double param2){
        //triggerDrive(param1, param2);
    }

    /**
     * getPigeonGyro
     * <p>returns yaw, pitch, and roll of the pigeon IMU
     */
    // public void getPigeonGyro(){
    //     double[] ypr = new double[3];
    //     testPigeon.getYawPitchRoll(ypr);
    //     SmartDashboard.putNumber("Yaw: ", ypr[0]);
    //     SmartDashboard.putNumber("Pitch: ", ypr[1]);
    //     SmartDashboard.putNumber("Roll: ", ypr[2]);
    
    // }
    /**
     * stop()
     * Stops the drive motors
     * Use this in auto to stop the drivetrain inbetween commands
     */ 
    public void stop() {
        System.out.println("The motors have been set to 0");
        // leftTop.set(ControlMode.PercentOutput, 0);
        // rightTop.set(ControlMode.PercentOutput, 0);
    }
    /**
     * driveStraightToEcnoderCounts()
     * Uses the TalonSRX PID to drive to a certain number of counts
     * @param counts specify encoder counts to drive to
     * @param backwards false to drive forward; true to drive backwards
     * @param useGyro true to use NavX to correct path and keep straight
     */ 
    public void driveStraightToEncoderCounts(int counts, boolean backwards, boolean useGyro) {
        // if(backwards) {
        //     //TODO: make sure gyro is at zero when this starts
        //     //getEncoderPosition gives a measure of encoder counts that is used as actual in PID loop
        //     pidOutputLeft = pidLeft.calculateOutput(counts, -getLeftEncoderPosition());
        //     pidOutputRight = pidRight.calculateOutput(counts, -getRightEncoderPosition());
        //     pidOutputNavX = pidNavX.calculateOutput(0, getGyroAngle());
        //     //Prints the pidOutput to the console
        //     System.out.println("bLeft: " + pidOutputLeft);
        //     System.out.println("bRight: " + pidOutputRight);            
        //     if(useGyro) {
        //         //TODO: See if one of the PID NavX values needs to be negated
        //         leftTop.set(ControlMode.PercentOutput, -pidOutputLeft + pidOutputNavX);
        //         rightTop.set(ControlMode.PercentOutput, pidOutputRight + pidOutputNavX);
        //     } else {
        //         leftTop.set(ControlMode.PercentOutput, -pidOutputLeft);
        //         rightTop.set(ControlMode.PercentOutput, pidOutputRight);
        //     }
        // } else {
        //     pidOutputLeft = pidLeft.calculateOutput(counts, getLeftEncoderPosition());
        //     pidOutputRight = pidRight.calculateOutput(counts, getRightEncoderPosition());
        //     pidOutputNavX = pidNavX.calculateOutput(0, getGyroAngle());
        //     System.out.println("Left: " + pidOutputLeft);
        //     System.out.println("Right: " + pidOutputRight);
        //     if(useGyro) {
        //         leftTop.set(ControlMode.PercentOutput, pidOutputLeft + pidOutputNavX);
        //         rightTop.set(ControlMode.PercentOutput, -pidOutputRight + pidOutputNavX);
        //     } else {
        //         leftTop.set(ControlMode.PercentOutput, pidOutputLeft);
        //         rightTop.set(ControlMode.PercentOutput, -pidOutputRight);
        //     }
        // }
    }
    /**
     * getGyroAngle()
     * @return returns the navX angle (yaw)
     */
    public double getGyroAngle() {
        // return navX.getYaw();
        return 0;
    }

    /**
     * driveforPower()
     * <p>sets drivetrain to drive for a percentage output between -1 and 1
     * @param power percentage outuput between -1 and 1
     */
    public void driveForPower(double power){
        // leftTop.set(ControlMode.PercentOutput, -power);
        // rightTop.set(ControlMode.PercentOutput, -power);
    }

    /**
     * resetHeading()
     * Resets navX gyro heading
     */ 
    public void resetHeading() {
        System.out.println("The heading has been reset");
        // navX.reset();
    }
    //TODO: Move to command
    /**
     * turnToHeading()
     * Turns the robot to a specified heading using PIDCalc and the navX
     * @param heading heading to turn to
     */ 
    public void turnToHeading(double heading) {
        // pidOutputNavX = pidNavX.calculateOutput(heading, navX.getYaw());
        // leftTop.set(ControlMode.PercentOutput, pidOutputNavX);
        // rightTop.set(ControlMode.PercentOutput, -pidOutputNavX);
        // SmartDashboard.putNumber("PidOutputNavX", pidOutputNavX);
        // SmartDashboard.putBoolean("reachHeading", reachedHeading(heading));
    }

    /**
     * keepHeading()
     * <p> a modified drive for power that uses NavX to correct path and keep straight
     * @param power percentage output between -1 and 1
     */
    public void keepHeading(double power){
        // pidOutputNavX = pidNavX.calculateOutput(0, navX.getYaw());
        // leftTop.set(ControlMode.PercentOutput, power + pidOutputNavX);
        // rightTop.set(ControlMode.PercentOutput, power - pidOutputNavX);
    }

    /**
     * reachedHeading()
     * <p>Determines if the drivetrain has reached the target heading
     * <p>Threshold is currently 4 degrees
     * @param heading heading to be reached
     * @return returns true if the robot is within 2 degrees of wanted heading
     */ 
    public boolean reachedHeading(double heading) {
        // return (navX.getYaw() <= (heading + 2) && navX.getYaw() >= (heading - 2));
        //This is a placeholder. Delete whenever the orignal code is uncommented
        return false;
    }       
     /**
     * getLeftEncoderPosition()
     * @return returns the left encoder position in counts
     */ 
    public int getLeftEncoderPosition() {
        // return leftTop.getSelectedSensorPosition(0);
        //This is a placeholder. Delete whenever the orignal code is uncommented
        return 0;
    }

    /**
     * getRightEncoderPosition()
     * @return returns the right encoder position in counts
     */ 
    public int getRightEncoderPosition() {
        // return -rightTop.getSelectedSensorPosition(0);
        //This is a placeholder. Delete whenever the orignal code is uncommented
        return 0;
    }

    /**
     * setLeftDrivePower()
     * Use this for motion profiling to set the left drive power
     * @param power sets the left drive power
     */ 
    public void setLeftDrivePower(double power) {
        // leftTop.set(ControlMode.PercentOutput, power);
    }

    /**
     * shiftHigh()
     * <p>Shifts the drivetrain into high gear
     */ 
    public void shiftHigh() {
        // shifter.set(DoubleSolenoid.Value.kForward);
        System.out.println("The shfiters have been set to High");
        States.shiftState = States.ShfitStates.HIGH_SHIFT;
    }

    /**
     * shiftLow()
     * <p>Shifts the drivetrain into low gear
     */ 
    public void shiftLow() {
        System.out.println("The shifters have been set to Low");
        // shifter.set(DoubleSolenoid.Value.kReverse);
        States.shiftState = States.ShfitStates.LOW_SHIFT;
    }

    /**
     * noShiftInput()
     * Leaves the shifters where they're at
     */ 
    public void noShiftInput() {
        System.out.println("The shifters have no Input");
        // shifter.set(DoubleSolenoid.Value.kOff);
    }
    public void setNavXPID(double kP, double kI, double kD, double kF){
        // NavX.setPIDParamaters(kP, kI, kD, kF);
        System.out.println("The NavX has been set to: " + kP + " " + kI + " " + kD + " " + kF);
    }
    public void resetNavXPID(){
        // NavX.resetPID();
        System.out.println("The navX PID has been reset");
    }
}
