package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.PIDCalc;
import frc.robot.Robot;
import frc.robot.RobotMap;
//import frc.robot.DynamicVisionPipeline;
import frc.robot.commands.teleop.TeleopDriveCommand;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.commands.teleop.TeleopDriveCommand;


/**
 * @author Keanu Clark
 * @version v1.0
 */ 

public class DriveSystem extends Subsystem {

    //Motor Controller Declarations
    private TalonSRX leftTop;
    private TalonSRX rightTop;

    private VictorSPX leftFront;
    private VictorSPX leftRear;
    private VictorSPX rightFront;
    private VictorSPX rightRear;
    //Shifter
    private DoubleSolenoid shifter;
    //Gyro
    public AHRS navX;
    //PID Gyro
    public PIDCalc pidNavX;
    public double pidOutputNavX;
    //PID Left
    public PIDCalc pidLeft;
    private double pidOutputLeft;
    //PID Right
    public PIDCalc pidRight;
    private double pidOutputRight;
    //PID Vision X 
    public PIDCalc pidXvalue;
    public double pidOutputXvalue;
    //PID Vision Area
    public PIDCalc pidAvalue;
    public double pidOutputAvalue;
    //Vision Right and Left
    public double visionLeft;
    public double visionRight;

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
        
         // Initialize TalonSRX and VictorSPX objects
         leftFront = new VictorSPX(RobotMap.leftFrontDrive);
         leftTop = new TalonSRX(RobotMap.leftTopDrive);
         leftRear = new VictorSPX(RobotMap.leftRearDrive);
 
         rightFront = new VictorSPX(RobotMap.rightFrontDrive);
         rightTop = new TalonSRX(RobotMap.rightTopDrive);
         rightRear = new VictorSPX(RobotMap.rightRearDrive);
 
         // Initialize DoubleSolenoid shifter object
         shifter = new DoubleSolenoid(RobotMap.shifterPort1, RobotMap.shifterPort2);
         
         // Set the front drive motors to follow the rear
         leftFront.follow(leftTop);
         leftRear.follow(leftTop);
         rightFront.follow(rightTop);
         rightRear.follow(rightTop);
 
         // Config PF on left side
         leftRear.config_kP(0, 0.25, 100);
         leftRear.config_kF(0, 1, 100);
 
         // Config PF on right side
         rightRear.config_kP(0, 0.25, 100);
         rightRear.config_kF(0, 1, 100);
 
         // Init the navX, Pathfinder, and PIDCalc
         navX = new AHRS(SPI.Port.kMXP);
         pidNavX = new PIDCalc(0.0005, 0.1, 50, 0, "NavX");
         pidLeft = new PIDCalc(0.0005, 0, 0, 0, "Left");
         pidRight = new PIDCalc(0.0005, 0, 0, 0, "Right");
         pidXvalue = new PIDCalc(0.007, 0.0, 0.0, 0.0, "CenterX");
         pidAvalue = new PIDCalc(0.0004, 0.0, 0.0, 0.0, "area");
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopDriveCommand());
    }

    /**
     * tankDrive()
     * Sets manual control of the drivetrain for teleop
     * @param left sets the left drive power
     * @param right sets the right drive power
     */
    public void tankDrive(double left, double right) {
        leftTop.set(ControlMode.PercentOutput, -left);
        rightTop.set(ControlMode.PercentOutput, right);
    }

    public void triggerDrive(double throttle, double steer){
        double leftDrive;
        double rightDrive;
        leftDrive = throttle + steer;
        rightDrive = throttle - steer;
        tankDrive(leftDrive, rightDrive);
    }

    /**
     * stop()
     * Stops the drive motors
     * Use this in auto to stop the drivetrain inbetween commands
     */ 
    public void stop() {
        leftTop.set(ControlMode.PercentOutput, 0);
        rightTop.set(ControlMode.PercentOutput, 0);
    }
    /**
     * driveStraightToEcnoderCounts()
     * Uses the TalonSRX PID to drive to a certain number of counts
     * @param counts specify encoder counts to drive to
     */ 
    public void driveStraightToEncoderCounts(int counts, boolean backwards, boolean useGyro) {
        if(backwards) {
            pidOutputLeft = pidLeft.calculateOutput(counts, -getLeftEncoderPosition());
            pidOutputRight = pidRight.calculateOutput(counts, -getRightEncoderPosition());
            pidOutputNavX = pidNavX.calculateOutput(0, getGyroAngle());
            System.out.println("bLeft: " + pidOutputLeft);
            System.out.println("bRight: " + pidOutputRight);            
            if(useGyro) {
                leftTop.set(ControlMode.PercentOutput, -pidOutputLeft + pidOutputNavX);
                rightTop.set(ControlMode.PercentOutput, pidOutputRight + pidOutputNavX);
            } else {
                leftTop.set(ControlMode.PercentOutput, -pidOutputLeft);
                rightTop.set(ControlMode.PercentOutput, pidOutputRight);
            }
        } else {
            pidOutputLeft = pidLeft.calculateOutput(counts, getLeftEncoderPosition());
            pidOutputRight = pidRight.calculateOutput(counts, getRightEncoderPosition());
            pidOutputNavX = pidNavX.calculateOutput(0, getGyroAngle());
            System.out.println("Left: " + pidOutputLeft);
            System.out.println("Right: " + pidOutputRight);
            if(useGyro) {
                leftTop.set(ControlMode.PercentOutput, pidOutputLeft + pidOutputNavX);
                rightTop.set(ControlMode.PercentOutput, -pidOutputRight + pidOutputNavX);
            } else {
                leftTop.set(ControlMode.PercentOutput, pidOutputLeft);
                rightTop.set(ControlMode.PercentOutput, -pidOutputRight);
            }
        }
    }
    /**
     * getGyroAngle()
     * @return returns the navX angle (yaw)
     */
    public double getGyroAngle() {
        return navX.getYaw();
    }

    public double getGyro360()  {
        if(navX.getYaw() < 0){
            return navX.getYaw() + 360;
        }
        else{
            return navX.getYaw();
        }
    }

    public void driveForPower(double power){
        leftFront.set(ControlMode.PercentOutput, -power*0.85);
        leftRear.set(ControlMode.PercentOutput,-power*0.85);
        rightFront.set(ControlMode.PercentOutput, -power);
        rightRear.set(ControlMode.PercentOutput, -power);

    }

    /**
     * resetHeading()
     * Resets navX gyro heading
     */ 
    public void resetHeading() {
        navX.reset();
    }
    /**
     * turnToHeading()
     * Turns the robot to a specified heading using PIDCalc and the navX
     * @param heading heading to turn to
     */ 
    public void turnToHeading(double heading) {
        pidOutputNavX = pidNavX.calculateOutput(heading, navX.getYaw());
        leftFront.set(ControlMode.PercentOutput, pidOutputNavX);
        leftRear.set(ControlMode.PercentOutput, pidOutputNavX);
        rightFront.set(ControlMode.PercentOutput, -pidOutputNavX);
        rightRear.set(ControlMode.PercentOutput, -pidOutputNavX);
        SmartDashboard.putNumber("PidOutputNavX", pidOutputNavX);
        SmartDashboard.putBoolean("reachHeading", reachedHeading(heading));
    }

    public void keepHeading(double power){
        pidOutputNavX = pidNavX.calculateOutput(0, navX.getYaw());
        leftFront.set(ControlMode.PercentOutput, power*0.85 + pidOutputNavX);
        leftRear.set(ControlMode.PercentOutput, power*0.85 + pidOutputNavX);
        rightFront.set(ControlMode.PercentOutput, power - pidOutputNavX);
        rightRear.set(ControlMode.PercentOutput, power - pidOutputNavX);
    }
    public void turnToVisionTarget() {
        pidOutputXvalue = pidXvalue.calculateOutput(Robot.visionSystem.DesiredX, Robot.visionSystem.centerX);
        leftFront.set(ControlMode.PercentOutput, pidOutputXvalue);
        leftRear.set(ControlMode.PercentOutput, pidOutputXvalue);
        rightFront.set(ControlMode.PercentOutput, -pidOutputXvalue);
        rightRear.set(ControlMode.PercentOutput, -pidOutputXvalue);
    }
    /**
     * reachedHeading()
     * Determines if the drivetrain has reached the target heading
     * @param heading heading to be reached
     * @return returns true if the robot is within 2 degrees of wanted heading
     */ 
    public boolean reachedHeading(double heading) {
        return (navX.getYaw() <= (heading + 2) && navX.getYaw() >= (heading - 2));
    }       
    public boolean reachedHeadingL(double heading) {
        return (navX.getYaw() <= (heading + 5) && navX.getYaw() >= (heading - 5));
    }
     /**
     * getLeftEncoderPosition()
     * @return returns the left encoder position in counts
     */ 
    public int getLeftEncoderPosition() {
        return leftTop.getSelectedSensorPosition(0);
    }

    /**
     * getRightEncoderPosition()
     * @return returns the right encoder position in counts
     */ 
    public int getRightEncoderPosition() {
        return -rightTop.getSelectedSensorPosition(0);
    }

    /**
     * setLeftDrivePower()
     * Use this for motion profiling to set the left drive power
     * @param power sets the left drive power
     */ 
    public void setLeftDrivePower(double power) {
        leftTop.set(ControlMode.PercentOutput, power);
    }


  /*  public void lineUpWithTape(){
        pidOutputXvalue = pidXvalue.calculateOutput(120, Robot.visionSystem.centerX);
        pidOutputAvalue = pidAvalue.calculateOutput(800, Robot.visionSystem.targetArea);
        visionLeft = pidOutputXvalue + pidOutputAvalue;
        visionRight = -pidOutputXvalue + pidOutputAvalue;
        tankDrive(visionLeft, visionRight);
    }*/

    /**
     * shiftHigh()
     * Shifts the drivetrain into high gear
     */ 
    public void shiftHigh() {
        shifter.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * shiftLow()
     * Shifts the drivetrain into low gear
     */ 
    public void shiftLow() {
        shifter.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * noShiftInput()
     * Leaves the shifters where they're at
     */ 
    public void noShiftInput() {
        shifter.set(DoubleSolenoid.Value.kOff);
    }
}
