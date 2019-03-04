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
   private VictorSPX driver;
   private TalonSRX levitator;
   private AHRS navX;
   private double startPosition = 0;
   private double pidOutput = 0;
   PIDCalc pid = new PIDCalc(1.2, 0, 0, 0, "Climber");

  public Climber() {
    driver = new VictorSPX(RobotMap.climbDriveMotor);
    levitator = new TalonSRX(RobotMap.levitator);
    navX = new AHRS(SPI.Port.kMXP);
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

  public void driveLevitator(double percent) {
      levitator.set(ControlMode.PercentOutput, percent);
  }

  public void getNavXPitch() {
      System.out.println("NAVX PITCH +++++++++++++++ : " + navX.getPitch() + " +++++++++++++++++++ \n");
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
