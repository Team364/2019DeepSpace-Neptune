package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
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

  public PigeonIMU pigeonTesting;

  public DriveTrain() {

    //dont know number at moment
    pigeonTesting = new PigeonIMU(100);

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

    sh = new DoubleSolenoid(RobotMap.primaryPCM, RobotMap.shifterPort1, RobotMap.shifterPort2);
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
  }

  public void closedLoop(double target){
    leftDrive.set(ControlMode.MotionMagic, target);
    rightDrive.set(ControlMode.MotionMagic, target);
  }

  public void turnClosedLoop(double target){
    //TODO: figure which one is negative (Neptune.driveTrain.turnClosedLoop)
    leftDrive.set(ControlMode.MotionMagic, -target);
    rightDrive.set(ControlMode.MotionMagic, target);
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

  public double getYaw(){
    double [] ypr = new double[3];
    pigeonTesting.getYawPitchRoll(ypr);
    //gets first element which is yaw
    return ypr[0];
  }

  public double getPitch(){
    double [] ypr = new double[3];
    pigeonTesting.getYawPitchRoll(ypr);
    //gets second element which is pitch
    return ypr[2];
  }

  public double getRoll(){
    double [] ypr = new double[3];
    pigeonTesting.getYawPitchRoll(ypr);
    //gets third element which is roll
    return ypr[3];
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

  public void resetEncoders() {
    leftDrive.setSelectedSensorPosition(0);
    rightDrive.setSelectedSensorPosition(0);
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
