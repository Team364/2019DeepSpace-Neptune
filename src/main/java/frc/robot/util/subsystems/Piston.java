package frc.robot.util.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Piston opens, closes, and has no input
 */
public class Piston extends Subsystem {

  private DoubleSolenoid piston;
  private String name;
  private Piston instance;
  /** State tracks what position piston is in */
      public enum PistonStates{
        OPEN,
        CLOSED
    }
    public PistonStates pistonState = PistonStates.CLOSED;

  public Piston(DoubleSolenoid piston, String name){
    this.piston = piston;
    this.name = name;
  }
    /**
   * Returns the {@link Piston}, creating it if one does not exist.
   *
   * @return the {@link Piston}
   */
  public synchronized Piston getInstance() {
    if (instance == null) {
      instance = new Piston(this.piston, this.name);
    }
    return instance;
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

  public boolean noCommand(){
    if(this.getCurrentCommandName() == ""){
      return true;
    }else{
      return false;
    }
  }
  
  /**Treat as abstract */
  @Override
  public void initDefaultCommand() {
  }
  public void postSmartDashVars(){
    String string = this.name + " Piston State";
    String com = this.name + " Current Command";
    SmartDashboard.putString(string, pistonState.toString());
    SmartDashboard.putString(com, this.getCurrentCommandName());
  }
}
