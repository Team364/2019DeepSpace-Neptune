package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import frc.robot.States;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.misc.Piston;
import edu.wpi.first.wpilibj.PWM;
import static frc.robot.Conversions.*;

public class Elevator extends Subsystem {

  private static Elevator Instance = null;
  private TalonSRX arm;
  private TalonSRX lift;
  private TalonSRX liftSlave;
  public double TargetHeight;
  public double TargetAngle;
  private DoubleSolenoid fr;
  public Piston front;
  private PWM servoCamera; 
  private PigeonIMU pigeon;
  private PigeonIMU.GeneralStatus gen_status;
  double[] yaw = new double[3]; 
  

  public Elevator() {
    lift = new TalonSRX(RobotMap.topLift);
    liftSlave = new TalonSRX(RobotMap.bottomLift);
    liftSlave.follow(lift);
    liftSlave.setInverted(true);
    lift.setInverted(false);
    liftSlave.setNeutralMode(NeutralMode.Brake);
    servoCamera = new PWM(RobotMap.servoCamera);
    pigeon = new PigeonIMU(liftSlave);
    gen_status = new PigeonIMU.GeneralStatus();
    pigeon.getGeneralStatus(gen_status);
    pigeon.setYaw(0);
    arm = new TalonSRX(RobotMap.arm);
    fr = new DoubleSolenoid(1, 0, 7);
    front = new Piston(fr, "Front");
    lift.configFactoryDefault();
    arm.configFactoryDefault();
    lift.configPeakCurrentLimit(RobotMap.liftCurrentCeiling);
    lift.configPeakCurrentDuration(RobotMap.liftCurrentCeilingDuration);
    liftSlave.configPeakCurrentLimit(RobotMap.liftCurrentCeiling);
    lift.configPeakCurrentLimit(RobotMap.liftCurrentCeilingDuration);

    lift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, RobotMap.PIDLoopIdx, RobotMap.TimeoutMs);
    arm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, RobotMap.PIDLoopIdx, RobotMap.TimeoutMs);

    lift.setSensorPhase(RobotMap.liftReverse);
    lift.setInverted(RobotMap.liftReverseEncoder);
    arm.setSensorPhase(RobotMap.armReverse);
    arm.setInverted(RobotMap.armReverseEncoder);

    lift.setNeutralMode(NeutralMode.Brake);
    arm.setNeutralMode(NeutralMode.Brake);

    lift.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, RobotMap.TimeoutMs);
    lift.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.TimeoutMs);
    arm.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, RobotMap.TimeoutMs);
    arm.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.TimeoutMs);

    lift.configNominalOutputForward(RobotMap.liftNominalOutputForward, RobotMap.TimeoutMs);
    lift.configNominalOutputReverse(RobotMap.liftNominalOutputReverse, RobotMap.TimeoutMs);
    lift.configPeakOutputForward(RobotMap.liftPeakOutputForward, RobotMap.TimeoutMs);
    lift.configPeakOutputReverse(RobotMap.liftPeakOutputReverse, RobotMap.TimeoutMs);
    arm.configNominalOutputForward(RobotMap.armNominalOutputForward, RobotMap.TimeoutMs);
    arm.configNominalOutputReverse(RobotMap.armNominalOutputReverse, RobotMap.TimeoutMs);
    arm.configPeakOutputForward(RobotMap.armPeakOutputForward, RobotMap.TimeoutMs);
    arm.configPeakOutputReverse(RobotMap.armPeakOutputReverse, RobotMap.TimeoutMs);

    lift.selectProfileSlot(RobotMap.SlotIdx, RobotMap.PIDLoopIdx);
    lift.config_kF(RobotMap.SlotIdx, RobotMap.liftFgain, RobotMap.TimeoutMs);
    lift.config_kP(RobotMap.SlotIdx, RobotMap.liftPgain, RobotMap.TimeoutMs);
    lift.config_kI(RobotMap.SlotIdx, RobotMap.liftIgain, RobotMap.TimeoutMs);
    lift.config_kD(RobotMap.SlotIdx, RobotMap.liftDgain, RobotMap.TimeoutMs);

    arm.selectProfileSlot(RobotMap.SlotIdx, RobotMap.PIDLoopIdx);
    arm.config_kF(RobotMap.SlotIdx, RobotMap.armFgain, RobotMap.TimeoutMs);
    arm.config_kP(RobotMap.SlotIdx, RobotMap.armPgain, RobotMap.TimeoutMs);
    arm.config_kI(RobotMap.SlotIdx, RobotMap.armIgain, RobotMap.TimeoutMs);
    arm.config_kD(RobotMap.SlotIdx, RobotMap.armDgain, RobotMap.TimeoutMs);

    lift.configMotionCruiseVelocity(RobotMap.liftCruiseVelocity, RobotMap.TimeoutMs);
    lift.configMotionAcceleration(RobotMap.liftAcceleration, RobotMap.TimeoutMs);
    arm.configMotionCruiseVelocity(RobotMap.armCruiseVelocity, RobotMap.TimeoutMs);
    arm.configMotionAcceleration(RobotMap.armAcceleration, RobotMap.TimeoutMs);

    lift.setSelectedSensorPosition(0);
    arm.setSelectedSensorPosition(0);
  }
  public synchronized static Elevator getInstance() {
    if (Instance == null) {
        Instance = new Elevator();
    }
    return Instance;
}

  public double getCam(){
    return servoCamera.getRaw();
  }
  public double getYaw() {
    pigeon.getYawPitchRoll(yaw);
    return yaw[0];
  }

  public PigeonIMU getPigeon(){
    return pigeon;
  }
  public double getGyro(){
    return modulate360(getYaw());
  }

  public void resetYaw(double angle) {
    pigeon.setYaw(angle);
  }

  public void setClimbCruiseVelocity() {
    lift.configMotionCruiseVelocity(RobotMap.liftCruiseVelocityClimb, RobotMap.TimeoutMs);
  }

  public void setPlayCruiseVelocity() {
    lift.configMotionCruiseVelocity(RobotMap.liftCruiseVelocity, RobotMap.TimeoutMs);
  }
  public void elevateTo(double liftHeight, double armAngle) {
    lift.set(ControlMode.MotionMagic, liftHeight);
    arm.set(ControlMode.MotionMagic, armAngle);
    TargetHeight = liftHeight;
    TargetAngle = armAngle;
  }
  public void setCamera(int position){
    servoCamera.setRaw(position);
    
  }

  public void setLiftPosition(double liftHeight) {
    lift.set(ControlMode.MotionMagic, liftHeight);
  }

  public void setArmAngle(double armAngle) {
    arm.set(ControlMode.MotionMagic, armAngle);
  }

  public double getLiftPosition() {
    return lift.getSelectedSensorPosition(RobotMap.PIDLoopIdx);
  }

  public double getLiftVelocity() {
    return lift.getSelectedSensorVelocity(RobotMap.PIDLoopIdx);
  }

  public double getArmAngle() {
    return arm.getSelectedSensorPosition(RobotMap.PIDLoopIdx);
  }

  public double getArmVelocity() {
    return arm.getSelectedSensorVelocity(RobotMap.PIDLoopIdx);
  }

  public double getLiftCurrentDraw() {
    return lift.getOutputCurrent();
  }

  public double getArmCurrentDraw() {
    return arm.getOutputCurrent();
  }

  public void resetEncoders() {
    lift.setSelectedSensorPosition(0);
    arm.setSelectedSensorPosition(0);
  }
  public void stopLift() {
    lift.set(ControlMode.PercentOutput, 0);
  }

  public void stopArm() {
    arm.set(ControlMode.PercentOutput, 0);
  }

  public boolean elevatorPassive() {
    return States.actionState == States.ActionStates.PASSIVE;
  }

  @Override
  public void initDefaultCommand() {
  }

}
