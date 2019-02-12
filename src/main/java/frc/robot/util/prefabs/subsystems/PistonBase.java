package frc.robot.util.prefabs.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Piston opens, closes, and has no input
 */
public class PistonBase extends Subsystem {

  private DoubleSolenoid piston;
  private String name;
  /** State tracks what position piston is in */
      public enum PistonStates{
        OPEN,
        CLOSED
    }
    public PistonStates pistonState = PistonStates.CLOSED;

  public PistonBase(DoubleSolenoid piston, String name){
    this.piston = piston;
    this.name = name;
  }
    /**
     * open piston
     */ 
    public void open() {
      piston.set(DoubleSolenoid.Value.kForward);
      pistonState = PistonStates.OPEN;
  }

  /**
   * closePiston()
   */ 
  public void close() {
      piston.set(DoubleSolenoid.Value.kReverse);
      pistonState = PistonStates.CLOSED;
  }

  /**
   * noInput()
   * Leaves the piston where it is
   */ 
  public void noInput() {
      piston.set(DoubleSolenoid.Value.kOff);
  }
  /**Treat as abstract */
  @Override
  public void initDefaultCommand() {
  }
  public void postSmartDashVars(){
    String string = this.name + " Piston State";
    SmartDashboard.putString(string, pistonState.toString());
  }
}
