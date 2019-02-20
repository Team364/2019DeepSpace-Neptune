package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.defaultcommands.DriveOpenLoop;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {

  private TalonSRX leftDrive;
  private TalonSRX rightDrive; 
  private VictorSPX leftRearDriveSlave;
  private VictorSPX leftTopDriveSlave;
  private VictorSPX rightRearDriveSlave;
  private VictorSPX rightTopDriveSlave;

  private DoubleSolenoid shifter;
  
  public DriveTrain(){

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
        
        shifter = new DoubleSolenoid(RobotMap.primaryPCM, RobotMap.shifterPort1, RobotMap.shifterPort2);
        /* Factory default hardware to prevent unexpected behavior */
        rightDrive.configFactoryDefault();

        leftDrive.configFactoryDefault();

        /* Configure Sensor Source for Pirmary PID */
        rightDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, RobotMap.PIDLoopIdx, RobotMap.TimeoutMs);

        leftDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, RobotMap.PIDLoopIdx, RobotMap.TimeoutMs);

        /**
        * Configure Talon SRX Output and Sesnor direction accordingly
        * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
        * Phase sensor to have positive increment when driving Talon Forward (Green LED)
        */
        rightDrive.setSensorPhase(RobotMap.rightDriveReverse);
        rightDrive.setInverted(RobotMap.rightDriveReverseEncoder);

        leftDrive.setSensorPhase(RobotMap.leftDriveReverse);
        leftDrive.setInverted(RobotMap.leftDriveReverseEncoder);

        /* Set relevant frame periods to be at least as fast as periodic rate */
        rightDrive.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, RobotMap.TimeoutMs);
        rightDrive.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.TimeoutMs);

        leftDrive.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, RobotMap.TimeoutMs);
        leftDrive.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.TimeoutMs);
  
        /* Set the peak and nominal outputs */
        rightDrive.configNominalOutputForward(RobotMap.driveNominalOutputForward, RobotMap.TimeoutMs);
        rightDrive.configNominalOutputReverse(RobotMap.driveNominalOutputReverse, RobotMap.TimeoutMs);
        rightDrive.configPeakOutputForward(RobotMap.drivePeakOutputForward, RobotMap.TimeoutMs);
        rightDrive.configPeakOutputReverse(RobotMap.drivePeakOutputReverse, RobotMap.TimeoutMs);

        leftDrive.configNominalOutputForward(RobotMap.driveNominalOutputForward, RobotMap.TimeoutMs);
        leftDrive.configNominalOutputReverse(RobotMap.driveNominalOutputReverse, RobotMap.TimeoutMs);
        leftDrive.configPeakOutputForward(RobotMap.drivePeakOutputForward, RobotMap.TimeoutMs);
        leftDrive.configPeakOutputReverse(RobotMap.drivePeakOutputReverse, RobotMap.TimeoutMs);
  
        /* Set Motion Magic gains in slot0 - see documentation */
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
  
        /* Set acceleration and vcruise velocity - see documentation */
        rightDrive.configMotionCruiseVelocity(RobotMap.driveCruiseVelocity, RobotMap.TimeoutMs);
        rightDrive.configMotionAcceleration(RobotMap.driveAcceleration, RobotMap.TimeoutMs);

        leftDrive.configMotionCruiseVelocity(RobotMap.driveCruiseVelocity, RobotMap.TimeoutMs);
        leftDrive.configMotionAcceleration(RobotMap.driveAcceleration, RobotMap.TimeoutMs);
  
        /* Zero the sensor */
        rightDrive.setSelectedSensorPosition(0);

        leftDrive.setSelectedSensorPosition(0);
   
  }

  public void openLoop(double left, double right){
    leftDrive.set(ControlMode.PercentOutput, left);
    rightDrive.set(ControlMode.PercentOutput, -right);
  }
  public void stop(){
    leftDrive.set(ControlMode.PercentOutput, 0);
    rightDrive.set(ControlMode.PercentOutput, 0);
  }
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveOpenLoop());
  }
}
