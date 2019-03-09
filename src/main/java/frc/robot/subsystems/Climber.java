package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.misc.PIDCalc;
import edu.wpi.first.wpilibj.SPI;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Climber extends Subsystem {

   private static Climber Instance = null;
   public VictorSPX driver;
   public TalonSRX levitator;
   public VictorSPX forearms;
   private AHRS navX;
   private double startPosition = 0;
   private double pidOutput = 0;
   PIDCalc pid = new PIDCalc(1.2, 0, 0, 0, "Climber");

  public Climber() {
    driver = new VictorSPX(RobotMap.climbDriveMotor);
    levitator = new TalonSRX(RobotMap.levitator);
    forearms = new VictorSPX(RobotMap.forearms);
    navX = new AHRS(SPI.Port.kMXP);

    levitator.configMotionCruiseVelocity(RobotMap.levitatorCruiseVelocity, RobotMap.TimeoutMs);
    levitator.configMotionAcceleration(RobotMap.levitatorAcceleration, RobotMap.TimeoutMs);
    levitator.setSensorPhase(RobotMap.levitatorSensorPhase);
    levitator.setInverted(true);
    levitator.configPeakOutputForward(0.9);
    levitator.configPeakOutputReverse(-0.9);
    levitator.setSelectedSensorPosition(0);

    levitator.selectProfileSlot(RobotMap.SlotIdx, RobotMap.PIDLoopIdx);
    levitator.config_kF(RobotMap.SlotIdx, RobotMap.levitatorFgain, RobotMap.TimeoutMs);
    levitator.config_kP(RobotMap.SlotIdx, RobotMap.levitatorPgain, RobotMap.TimeoutMs);
    levitator.config_kI(RobotMap.SlotIdx, RobotMap.levitatorIgain, RobotMap.TimeoutMs);
    levitator.config_kD(RobotMap.SlotIdx, RobotMap.levitatorDgain, RobotMap.TimeoutMs);

    forearms.setInverted(true);
    
  }

  public synchronized static Climber getInstance() {
    if (Instance == null) {
      Instance = new Climber();
    }
    return Instance;
  }

  public void levitateWithGyro(double angle){
    // Move motor to pre-specified encoder count\

    // Use PID here to keep gyro pitch level-ish
    pidOutput = pid.calculateOutput(angle, navX.getPitch());
    levitator.set(ControlMode.PercentOutput, pidOutput);
  }

  public void keepCurrentPosition() {
      double pos = levitator.getSelectedSensorPosition(0);
      levitator.set(ControlMode.MotionMagic, pos);
  }

  public void driveWheelsToWin(){
    // Turn on drive motors.. full steam ahead
    driver.set(ControlMode.Velocity, 300);
  }
  public void driveWheelsSlow(){
    driver.set(ControlMode.Velocity, 50);
  }
  public void driveLevitator(double percent) {
      levitator.set(ControlMode.PercentOutput, percent);
  }
  public void levitateToPos(double position){
    levitator.set(ControlMode.MotionMagic, position);
    System.out.println("Levitator is moving to " + position);
  }
  public void engageForarms(double percentOut){
    forearms.set(ControlMode.PercentOutput, percentOut);
  }
  public void stopForarms(){
    forearms.set(ControlMode.PercentOutput, 0);
  }


  public void getNavXPitch() {
   //   System.out.println("NAVX PITCH +++++++++++++++ : " + navX.getPitch() + " +++++++++++++++++++ \n");
  }

  public void stop() {
    // Stop the drive wheels
    driver.set(ControlMode.PercentOutput, 0);
  }

  public void unLevitate() {
    // Move levitator back to start position
    levitator.set(ControlMode.MotionMagic, startPosition);
  }

  @Override
  public void initDefaultCommand() {

  }
}
