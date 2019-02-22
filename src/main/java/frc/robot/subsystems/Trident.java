package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.defaultcommands.TridentControlLoop;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Trident extends Subsystem {

  private static Trident Instance = null;
  private VictorSPX intake;
  private VictorSPX intakeSlave;
  private DoubleSolenoid claw;
  private DoubleSolenoid lever;
  public enum ClawPistonStates{
    OPEN,
    CLOSED
  }
  public enum LeverPistonStates{
    OPEN,
    CLOSED
  }
  public ClawPistonStates clawPistonState = ClawPistonStates.CLOSED;
  public LeverPistonStates leverPistonState = LeverPistonStates.CLOSED;

  private Trident(){
    intake = new VictorSPX(RobotMap.rightClaw);
    
    intakeSlave = new VictorSPX(RobotMap.leftClaw);
    intakeSlave.follow(intake);

    claw = new DoubleSolenoid(RobotMap.primaryPCM, RobotMap.intakePort1, RobotMap.intakePort2);
    lever = new DoubleSolenoid(RobotMap.primaryPCM, RobotMap.leverPort1, RobotMap.leverPort2);

    intake.configPeakOutputForward(RobotMap.intakePeakOutputForward);
    intake.configPeakOutputReverse(RobotMap.intakePeakOutputReverse);
    intake.configNominalOutputReverse(RobotMap.intakeNominalOutputReverse);
    intake.configNominalOutputForward(RobotMap.intakeNominalOutputForward);
  }
  public synchronized static Trident getInstance() {
    if (Instance == null) {
        Instance = new Trident();
    }
    return Instance;
}

  public void runIntake(double power) {
    intake.set(ControlMode.PercentOutput, power);
  }

  public void stopIntake() {
    runIntake(0);
  }
  public double getRawOutput(){
    return intake.getMotorOutputPercent();
  }
  
  public boolean tridentInactive(){
    return ((intake.getSelectedSensorVelocity() ==0) && leverInactive() && clawInactive());
  }
    public void openClaw() {
      claw.set(DoubleSolenoid.Value.kReverse);
      clawPistonState = ClawPistonStates.OPEN;
    }
  public void closeClaw() {
      claw.set(DoubleSolenoid.Value.kForward);
      clawPistonState = ClawPistonStates.CLOSED;
    }
    public void noInputClaw() {
      claw.set(DoubleSolenoid.Value.kOff);
    }
    public void openLever() {
      lever.set(DoubleSolenoid.Value.kReverse);
      clawPistonState = ClawPistonStates.OPEN;
    }
  public void closeLever() {
      lever.set(DoubleSolenoid.Value.kForward);
      clawPistonState = ClawPistonStates.CLOSED;
    }

    public void noInputLever() {
      lever.set(DoubleSolenoid.Value.kOff);
    }
    public boolean leverInactive(){
     return lever.get() == DoubleSolenoid.Value.kOff;
    }
    public boolean clawInactive(){
      return claw.get() == DoubleSolenoid.Value.kOff;
     }
     public void open(){
       openLever();
       openClaw();
     }
     public void close(){
       closeLever();
       closeClaw();
     }
     public void noInput(){
       noInputLever();
       noInputClaw();
     }
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TridentControlLoop());
  }
}
