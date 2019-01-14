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
import frc.robot.commands.teleop.TeleopDriveCommand;


/**
 * @author Keanu Clark
 * @version v1.0
 */ 

public class DriveSystem extends Subsystem {

    public VictorSP leftFront;
    public VictorSP leftRear;
    public VictorSP rightFront;
    public VictorSP rightRear;
    public DoubleSolenoid shifter;
    public AHRS navX;
    public PIDCalc pidNavX;
    public PIDCalc pidLeft;
    public PIDCalc pidRight;
    public PIDCalc pidRampDown;
    public double pidOutputNavX;
    public double pidOutputLeft;
    public double pidOutputRight;
    public double pidOutputRampDown;

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
        leftFront.set(-left);
        leftRear.set(-left);
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
    /**
     * reachedHeading()
     * Determines if the drivetrain has reached the target heading
     * @param heading heading to be reached
     * @return returns true if the robot is within 2 degrees of wanted heading
     */ 
    public boolean reachedHeading(double heading) {
        if(navX.getYaw() <= (heading + 2) && navX.getYaw() >= (heading - 2)) {
            return true;
        } else {
            return false;
        }
    }       
    public boolean reachedHeadingL(double heading) {
        if(navX.getYaw() <= (heading + 5) && navX.getYaw() >= (heading - 5)) {
            return true;
        } else {
            return false;
        }
    }      


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
