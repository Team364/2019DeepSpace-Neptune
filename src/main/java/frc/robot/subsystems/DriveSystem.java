package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.VictorSP;
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


/**
 * @author Keanu Clark
 * @version v1.0
 */ 

public class DriveSystem extends Subsystem {

    private VictorSP leftFront;
    private VictorSP leftRear;
    private VictorSP rightFront;
    private VictorSP rightRear;
    private DoubleSolenoid shifter;
    public AHRS navX;
    public PIDCalc pidNavX;
    //public PIDCalc pidLeft;
    //public PIDCalc pidRight;
    public double pidOutputNavX;
    private double pidOutputLeft;
    private double pidOutputRight;
    private double pidOutputRampDown;
    public PIDCalc pidXvalue;
    public double pidOutputXvalue;
    public PIDCalc pidAvalue;
    public double pidOutputAvalue;
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
        leftFront = new VictorSP(RobotMap.leftFrontDrive);
        leftRear = new VictorSP(RobotMap.leftRearDrive);
        rightFront = new VictorSP(RobotMap.rightFrontDrive);
        rightRear = new VictorSP(RobotMap.rightRearDrive);

    
        // Initialize DoubleSolenoid shifter object
        shifter = new DoubleSolenoid(RobotMap.shifterPort1, RobotMap.shifterPort2);
        
	    // Init the navX, Pathfinder, and PIDCalc
        navX = new AHRS(SPI.Port.kMXP);
        pidNavX = new PIDCalc(0.0005, 0.1, 50, 0, "NavX");
        pidXvalue = new PIDCalc(0.007, 0.0, 0.0, 0.0, "follow");
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
        leftFront.set(-left*0.85);
        leftRear.set(-left*0.85);
        rightFront.set(-right);
        rightRear.set(-right);
    }

    /**
     * stop()
     * Stops the drive motors
     * Use this in auto to stop the drivetrain inbetween commands
     */ 
    public void stop() {
        leftFront.set(0);
        leftRear.set(0);
        rightFront.set(0);
        rightRear.set(0);
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
        leftFront.set(-power*0.85);
        leftRear.set(-power*0.85);
        rightFront.set(-power);
        rightRear.set(-power);

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
        leftFront.set(pidOutputNavX);
        leftRear.set(pidOutputNavX);
        rightFront.set(-pidOutputNavX);
        rightRear.set(-pidOutputNavX);
        SmartDashboard.putNumber("PidOutputNavX", pidOutputNavX);
        SmartDashboard.putBoolean("reachHeading", reachedHeading(heading));
    }

    public void keepHeading(double power){
        pidOutputNavX = pidNavX.calculateOutput(0, navX.getYaw());
        leftFront.set(power*0.85 + pidOutputNavX);
        leftRear.set(power*0.85 + pidOutputNavX);
        rightFront.set(power - pidOutputNavX);
        rightRear.set(power - pidOutputNavX);
    }
    public void turnToDisk(double X) {
        pidOutputNavX = pidNavX.calculateOutput(X, Robot.visionSystem.centerX);
        leftFront.set(pidOutputNavX);
        leftRear.set(pidOutputNavX);
        rightFront.set(-pidOutputNavX);
        rightRear.set(-pidOutputNavX);
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
