package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
<<<<<<< HEAD
import frc.robot.Neptune;
import frc.robot.commands.DrivetrainCommand;
import frc.robot.misc.math.Rotation2;
import frc.robot.misc.math.Vector2;

import static frc.robot.RobotMap.*;
import static frc.robot.Neptune.*;
import frc.robot.subsystems.SwerveMod.*;

public class Drivetrain extends Subsystem {

    private static Drivetrain Instance = null;
	/*
	 * 0 is Front Right
	 * 1 is Front Left
	 * 2 is Back Left
	 * 3 is Back Right
	 */
    private SwerveMod[] mSwerveModules;
    private int w = WHEELBASE;
    private int t = TRACKWIDTH;
    private Command zero;
    public static int cycles ;

    public Drivetrain() {
        cycles = 0;
            mSwerveModules = new SwerveMod[] {
                    new SwerveMod(0,
                            new Vector2(TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                            new TalonSRX(FRANGLE),
                            new TalonSRX(FRDRIVE),
                            MOD0OFFSET,
                            false),
                    new SwerveMod(1,
                            new Vector2(-TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                            new TalonSRX(FLANGLE),
                            new TalonSRX(FLDRIVE),
                            MOD1OFFSET,
                            true),
                    new SwerveMod(2,
                            new Vector2(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
                            new TalonSRX(BLANGLE),
                            new TalonSRX(BLDRIVE),
                            MOD2OFFSET,
                            false),
                    new SwerveMod(3,
                            new Vector2(TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
                            new TalonSRX(BRANGLE),
                            new TalonSRX(BRDRIVE),
                            MOD3OFFSET,
                            false)
            };
            //mSwerveModules[2].setSensorPhase(true);
            
    } 

    public synchronized static Drivetrain getInstance() {
        if (Instance == null) {
          Instance = new Drivetrain();
        }
        return Instance;
      }


    public SwerveMod getSwerveModule(int i) {
        return mSwerveModules[i];
    }

    public void holonomicDrive(Vector2 translation, double rotation, boolean fieldOriented) {
        if (fieldOriented) {
            // need to get pigeon vector
            translation = translation.rotateBy(Rotation2.fromDegrees(Neptune.elevator.getYaw()).inverse());

        }

        for (SwerveMod mod : getSwerveModules()) {
            Vector2 velocity = mod.getModulePosition().normal().scale(rotation).add(translation);
            mod.setTargetVelocity(velocity);
        }
        setKinematics();
    }
    public void setKinematics(){
        for (SwerveMod mod : getSwerveModules()){
            double targetAngle;
            double targetSpeed;

                targetAngle = mod.targetAngle;
                targetSpeed = mod.targetSpeed;

            final double currentAngle = mod.getCurrentAngle();

            // Change the target angle so the delta is in the range [-pi, pi) instead of [0, 2pi)
            double delta = targetAngle - currentAngle;
            if (delta >= Math.PI) {
                targetAngle -= 2.0 * Math.PI;
            } else if (delta < -Math.PI) {
                targetAngle += 2.0 * Math.PI;
            }

            // Deltas that are greater than 90 deg or less than -90 deg can be inverted so the total movement of the module
            // is less than 90 deg by inverting the wheel direction
            delta = targetAngle - currentAngle;
            if (delta > Math.PI / 2.0 || delta < -Math.PI / 2.0) {
                // Only need to add pi here because the target angle will be put back into the range [0, 2pi)
                targetAngle += Math.PI;

                targetSpeed *= -1.0;
            }

            // Put target angle back into the range [0, 2pi)
            targetAngle %= 2.0 * Math.PI;
            if (targetAngle < 0.0) {
                targetAngle += 2.0 * Math.PI;
            }
            mod.setTargetAngleVal(targetAngle);
            mod.setTargetSpeedVal(targetSpeed);
        }
    }
    public void updateKinematics(){
        for (SwerveMod mod : getSwerveModules()){
            SmartDashboard.putNumber("cycles", cycles);
            SmartDashboard.putNumber("targetAngle initial" + mod.moduleNumber, mod.targetAngle);
            mod.setTargetAngle(mod.targetAngle);
            mod.setTargetSpeed(mod.targetSpeed);
        }
    }

    public void stopDriveMotors() {
        for (SwerveMod module : mSwerveModules) {
            module.setTargetSpeed(0);
        }
    }
=======
import frc.robot.defaultcommands.DriveOpenLoop;
import frc.robot.misc.Piston;
import frc.robot.misc.Piston.PistonStates;
import frc.robot.RobotMap;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.networktables.NetworkTableInstance;

public class DriveTrain extends Subsystem {

  private static DriveTrain Instance = null;
  private TalonSRX leftDrive;
  private TalonSRX rightDrive;
  private VictorSPX leftRearDriveSlave;
  private VictorSPX leftTopDriveSlave;
  private VictorSPX rightRearDriveSlave;
  private VictorSPX rightTopDriveSlave;

  private DoubleSolenoid sh;
  public Piston shifter;


  public DriveTrain() {

    rightDrive = new TalonSRX(RobotMap.rightFrontDrive);
    leftDrive = new TalonSRX(RobotMap.leftFrontDrive);

    leftRearDriveSlave = new VictorSPX(RobotMap.leftRearDrive);
    leftTopDriveSlave = new VictorSPX(RobotMap.leftTopDrive);
    rightRearDriveSlave = new VictorSPX(RobotMap.rightRearDrive);
    rightTopDriveSlave = new VictorSPX(RobotMap.rightTopDrive);

    rightRearDriveSlave.follow(rightDrive);
    rightTopDriveSlave.follow(rightDrive);
    leftRearDriveSlave.follow(leftDrive);
    leftTopDriveSlave.follow(leftDrive);

    rightDrive.setNeutralMode(NeutralMode.Brake);
    leftDrive.setNeutralMode(NeutralMode.Brake);
    leftRearDriveSlave.setNeutralMode(NeutralMode.Brake);
    leftTopDriveSlave.setNeutralMode(NeutralMode.Brake);
    rightRearDriveSlave.setNeutralMode(NeutralMode.Brake);
    rightTopDriveSlave.setNeutralMode(NeutralMode.Brake);

    sh = new DoubleSolenoid(RobotMap.shifterPort1, RobotMap.shifterPort2);
    shifter = new Piston(sh, "Shifter");

    rightDrive.configFactoryDefault();
    leftDrive.configFactoryDefault();

    rightDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, RobotMap.PIDLoopIdx,
        RobotMap.TimeoutMs);
    leftDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, RobotMap.PIDLoopIdx,
        RobotMap.TimeoutMs);

    rightDrive.setSensorPhase(RobotMap.rightDriveReverse);
    rightDrive.setInverted(RobotMap.rightDriveReverseEncoder);
    leftDrive.setSensorPhase(RobotMap.leftDriveReverse);
    leftDrive.setInverted(RobotMap.leftDriveReverseEncoder);
    
>>>>>>> parent of 38d4ef2... First Commit

    rightDrive.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, RobotMap.TimeoutMs);
    rightDrive.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.TimeoutMs);
    leftDrive.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, RobotMap.TimeoutMs);
    leftDrive.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.TimeoutMs);

    rightDrive.configNominalOutputForward(RobotMap.driveNominalOutputForward, RobotMap.TimeoutMs);
    rightDrive.configNominalOutputReverse(RobotMap.driveNominalOutputReverse, RobotMap.TimeoutMs);
    rightDrive.configPeakOutputForward(RobotMap.drivePeakOutputForward, RobotMap.TimeoutMs);
    rightDrive.configPeakOutputReverse(RobotMap.drivePeakOutputReverse, RobotMap.TimeoutMs);
    leftDrive.configNominalOutputForward(RobotMap.driveNominalOutputForward, RobotMap.TimeoutMs);
    leftDrive.configNominalOutputReverse(RobotMap.driveNominalOutputReverse, RobotMap.TimeoutMs);
    leftDrive.configPeakOutputForward(RobotMap.drivePeakOutputForward, RobotMap.TimeoutMs);
    leftDrive.configPeakOutputReverse(RobotMap.drivePeakOutputReverse, RobotMap.TimeoutMs);

    rightDrive.selectProfileSlot(RobotMap.SlotIdx, RobotMap.PIDLoopIdx);
    rightDrive.config_kF(RobotMap.SlotIdx, RobotMap.driveFgain, RobotMap.TimeoutMs);
    rightDrive.config_kP(RobotMap.SlotIdx, RobotMap.drivePgain, RobotMap.TimeoutMs);
    rightDrive.config_kI(RobotMap.SlotIdx, RobotMap.driveIgain, RobotMap.TimeoutMs);
    rightDrive.config_kD(RobotMap.SlotIdx, RobotMap.driveDgain, RobotMap.TimeoutMs);
    leftDrive.selectProfileSlot(RobotMap.SlotIdx, RobotMap.PIDLoopIdx);
    leftDrive.config_kF(RobotMap.SlotIdx, RobotMap.driveFgain, RobotMap.TimeoutMs);
    leftDrive.config_kP(RobotMap.SlotIdx, RobotMap.drivePgain, RobotMap.TimeoutMs);
    leftDrive.config_kI(RobotMap.SlotIdx, RobotMap.driveIgain, RobotMap.TimeoutMs);
    leftDrive.config_kD(RobotMap.SlotIdx, RobotMap.driveDgain, RobotMap.TimeoutMs);

    rightDrive.configMotionCruiseVelocity(RobotMap.driveCruiseVelocity, RobotMap.TimeoutMs);
    rightDrive.configMotionAcceleration(RobotMap.driveAcceleration, RobotMap.TimeoutMs);
    leftDrive.configMotionCruiseVelocity(RobotMap.driveCruiseVelocity, RobotMap.TimeoutMs);
    leftDrive.configMotionAcceleration(RobotMap.driveAcceleration, RobotMap.TimeoutMs);

    rightDrive.setSelectedSensorPosition(0);
    leftDrive.setSelectedSensorPosition(0);

  }
  public synchronized static DriveTrain getInstance() {
    if (Instance == null) {
        Instance = new DriveTrain();
    }
    return Instance;
}

  public void openLoop(double left, double right) {
    leftDrive.set(ControlMode.PercentOutput, left);
    rightDrive.set(ControlMode.PercentOutput, -right);
    SmartDashboard.putNumber("right value", rightDrive.getMotorOutputPercent());
    SmartDashboard.putNumber("left value", leftDrive.getMotorOutputPercent());
    SmartDashboard.putNumber("right input", right);


  }

  public void closedLoop(double target){
    leftDrive.set(ControlMode.MotionMagic, -target);
    rightDrive.set(ControlMode.MotionMagic, target);
  }

  public void turnClosedLoop(double target){
    leftDrive.set(ControlMode.MotionMagic, target);
    rightDrive.set(ControlMode.MotionMagic, target);
    SmartDashboard.putNumber("error", leftDrive.getClosedLoopError(RobotMap.PIDLoopIdx));

  }



  public void stop() {
    leftDrive.set(ControlMode.PercentOutput, 0);
    rightDrive.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveOpenLoop());
  }

  public boolean isShifterHigh() {
    return this.shifter.getPistonState() == PistonStates.OPEN;
  }
  
  public int getRightCounts(){
    return rightDrive.getSelectedSensorPosition(RobotMap.PIDLoopIdx);
  }
  
  public int getLeftCounts(){
    return leftDrive.getSelectedSensorPosition(RobotMap.PIDLoopIdx);
  }

  public int getLeftVelocity() {
    return leftDrive.getSelectedSensorVelocity();
  }

  public int getRightVelocity() {
    return rightDrive.getSelectedSensorVelocity();
  }
  public double getRightError(){
    return rightDrive.getClosedLoopError(RobotMap.PIDLoopIdx);
  }
  public double getLeftError(){
    return leftDrive.getClosedLoopError(RobotMap.PIDLoopIdx);
  }
  public void setPconfig(double pValue){
    rightDrive.config_kP(RobotMap.SlotIdx, pValue, RobotMap.TimeoutMs);
    leftDrive.config_kP(RobotMap.SlotIdx, pValue, RobotMap.TimeoutMs);
  }
  public void resetEncoders() {
    leftDrive.setSelectedSensorPosition(0);
    rightDrive.setSelectedSensorPosition(0);
  }
  public TalonSRX getRightDrive(){
    return rightDrive;
  }

  public void setTrackingMode() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3); //Turns LED on
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0); //Begin Processing Vision
  }

  public void setDriverCamMode() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1); //Turns LED off
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1); //Disable Vision Processing
  }
}
