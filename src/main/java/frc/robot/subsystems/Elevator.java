package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import frc.robot.States;
import frc.robot.defaultcommands.ElevatorManual;

public class Elevator extends Subsystem {

  private TalonSRX arm;
  private TalonSRX lift;
  private TalonSRX liftSlave;  
  public double TargetHeight;
  public double TargetAngle;

  public Elevator(){
        lift = new TalonSRX(RobotMap.rightLift);
        liftSlave = new TalonSRX(RobotMap.leftLift);
        liftSlave.follow(lift);
        liftSlave.setInverted(false);

        arm = new TalonSRX(RobotMap.arm);

        lift.configFactoryDefault();
        arm.configFactoryDefault();

        lift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, RobotMap.PIDLoopIdx, RobotMap.TimeoutMs);
        arm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, RobotMap.PIDLoopIdx, RobotMap.TimeoutMs);

        lift.setSensorPhase(RobotMap.liftReverse);
        lift.setInverted(RobotMap.liftReverseEncoder);
        arm.setSensorPhase(RobotMap.armReverse);
        arm.setInverted(RobotMap.armReverseEncoder);

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

  public void elevateTo(double liftHeight, double armAngle){
    System.out.println("The lift is moving to: " + liftHeight);
    System.out.println("The arm is moving to: " + armAngle);
    lift.set(ControlMode.MotionMagic, liftHeight);
    arm.set(ControlMode.MotionMagic, armAngle);
    TargetHeight = liftHeight;
    TargetAngle = armAngle;
  }
  public void setLiftPosition(double liftHeight){
    lift.set(ControlMode.MotionMagic, liftHeight);
    System.out.println("The lift is moving to: " + liftHeight);
  }
  public void setArmAngle(double armAngle){
    arm.set(ControlMode.MotionMagic, armAngle);
    System.out.println("The arm is moving to: " + armAngle);
  }

  public double getLiftPosition(){
    return lift.getSelectedSensorPosition(RobotMap.PIDLoopIdx);
  }
  public double getLiftVelocity(){
    return lift.getSelectedSensorVelocity(RobotMap.PIDLoopIdx);
  }
  public double getArmAngle(){
    return arm.getSelectedSensorPosition(RobotMap.PIDLoopIdx);
  }
  public double getArmVelocity(){
    return arm.getSelectedSensorVelocity(RobotMap.PIDLoopIdx);
  }
  public void resetEncoders(){
    lift.setSelectedSensorPosition(0);
    arm.setSelectedSensorPosition(0);
  }
  public void stopLift(){
    lift.set(ControlMode.PercentOutput, 0);
  }
  public void stopArm(){
    arm.set(ControlMode.PercentOutput, 0);
  }
  public boolean elevatorPassive(){
    return States.actionState == States.ActionStates.PASSIVE;
  }

  @Override
  public void initDefaultCommand() {
  }

}
