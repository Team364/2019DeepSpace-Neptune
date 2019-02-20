package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
/**
 * Add your docs here.
 */
public class ElevatorSystem extends Subsystem {

  private TalonSRX arm;
  private TalonSRX lift;
  private TalonSRX liftSlave;  
  private double TargetHeight;
  private double TargetAngle;
  /**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
  public static final int SlotIdx = 0;
/**
 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
 * now we just want the primary one.
 */
public static final int PIDLoopIdx = 0;
/**
 * set to zero to skip waiting for confirmation, set to nonzero to wait and
 * report to DS if action fails.
 */
public static final int TimeoutMs = 30;
  public ElevatorSystem(){
        lift = new TalonSRX(RobotMap.rightLift);
        liftSlave = new TalonSRX(RobotMap.leftLift);
        liftSlave.follow(lift);
        liftSlave.setInverted(false);

        arm = new TalonSRX(RobotMap.arm);

        /* Factory default hardware to prevent unexpected behavior */
        lift.configFactoryDefault();

        arm.configFactoryDefault();

        /* Configure Sensor Source for Pirmary PID */
        lift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PIDLoopIdx, TimeoutMs);

        arm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PIDLoopIdx, TimeoutMs);

        /**
        * Configure Talon SRX Output and Sesnor direction accordingly
        * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
        * Phase sensor to have positive increment when driving Talon Forward (Green LED)
        */
        lift.setSensorPhase(RobotMap.liftReverse);
        lift.setInverted(RobotMap.liftReverseEncoder);

        arm.setSensorPhase(RobotMap.armReverse);
        arm.setInverted(RobotMap.armReverseEncoder);

        /* Set relevant frame periods to be at least as fast as periodic rate */
        lift.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TimeoutMs);
        lift.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, TimeoutMs);

        arm.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TimeoutMs);
        arm.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, TimeoutMs);
  
        /* Set the peak and nominal outputs */
        lift.configNominalOutputForward(RobotMap.liftNominalOutputForward, TimeoutMs);
        lift.configNominalOutputReverse(RobotMap.liftNominalOutputReverse, TimeoutMs);
        lift.configPeakOutputForward(RobotMap.liftPeakOutputForward, TimeoutMs);
        lift.configPeakOutputReverse(RobotMap.liftPeakOutputReverse, TimeoutMs);

        arm.configNominalOutputForward(RobotMap.armNominalOutputForward, TimeoutMs);
        arm.configNominalOutputReverse(RobotMap.armNominalOutputReverse, TimeoutMs);
        arm.configPeakOutputForward(RobotMap.armPeakOutputForward, TimeoutMs);
        arm.configPeakOutputReverse(RobotMap.armPeakOutputReverse, TimeoutMs);
  
        /* Set Motion Magic gains in slot0 - see documentation */
        lift.selectProfileSlot(SlotIdx, PIDLoopIdx);
        lift.config_kF(SlotIdx, RobotMap.liftFgain, TimeoutMs);
        lift.config_kP(SlotIdx, RobotMap.liftPgain, TimeoutMs);
        lift.config_kI(SlotIdx, RobotMap.liftIgain, TimeoutMs);
        lift.config_kD(SlotIdx, RobotMap.liftDgain, TimeoutMs);

        arm.selectProfileSlot(SlotIdx, PIDLoopIdx);
        arm.config_kF(SlotIdx, RobotMap.armFgain, TimeoutMs);
        arm.config_kP(SlotIdx, RobotMap.armPgain, TimeoutMs);
        arm.config_kI(SlotIdx, RobotMap.armIgain, TimeoutMs);
        arm.config_kD(SlotIdx, RobotMap.armDgain, TimeoutMs);
  
        /* Set acceleration and vcruise velocity - see documentation */
        lift.configMotionCruiseVelocity(RobotMap.liftCruiseVelocity, TimeoutMs);
        lift.configMotionAcceleration(RobotMap.liftAcceleration, TimeoutMs);

        arm.configMotionCruiseVelocity(RobotMap.armCruiseVelocity, TimeoutMs);
        arm.configMotionAcceleration(RobotMap.armAcceleration, TimeoutMs);
  
        /* Zero the sensor */
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

  @Override
  public void initDefaultCommand() {
  }
}
