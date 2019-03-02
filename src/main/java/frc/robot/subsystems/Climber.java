package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.misc.PIDCalc;
//import frc.robot.misc.subsystems.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.DigitalInput;

public class Climber extends Subsystem {

   private static Climber Instance = null;
   private VictorSPX driver;
   private TalonSRX levitator;
   private double winPosition = 100.0;
   private double startPosition = 0;
   PIDCalc pid = new PIDCalc(.01, .01, 0, 0, "Climber");

//   private VictorSPX intakeSlave;
//   private DoubleSolenoid cl;
//   private DoubleSolenoid le;
//   public Piston claw;
//   public Piston lever;
//   public DigitalInput intakeLimit;

  public Climber() {
    driver = new VictorSPX(RobotMap.climbDriveMotor);
    levitator = new TalonSRX(RobotMap.levitator);

    //levitator.get

    // intake.configPeakOutputForward(RobotMap.intakePeakOutputForward);
    // intake.configPeakOutputReverse(RobotMap.intakePeakOutputReverse);
    // intake.configNominalOutputReverse(RobotMap.intakeNominalOutputReverse);
    // intake.configNominalOutputForward(RobotMap.intakeNominalOutputForward);

    // intake.setNeutralMode(NeutralMode.Coast);
    // intakeSlave.setNeutralMode(NeutralMode.Coast);

    // Reset encoders...?
  }

  public synchronized static Climber getInstance() {
    if (Instance == null) {
      Instance = new Climber();
    }
    return Instance;
  }

  public void levitateToWinPosition(){
    // Move motor to pre-specified encoder count\

    // Use PID here to keep gyro pitch level-ish
    levitator.set(ControlMode.MotionMagic, winPosition);
  }

  public void driveWheelsToWin(){
    // Turn on drive motors.. full steam ahead
    driver.set(ControlMode.PercentOutput, .4);
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
