package frc.robot.util.prefabs.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * Piston opens, closes, and has no input
 */
public class VictorBase extends Subsystem {

  private VictorSPX victor;
  private String name;
  private double Dampen;
  private VictorBase instance;

  public VictorBase(VictorSPX victor, double Dampen, String name){
    this.victor = victor;
    this.name = name;
    this.Dampen = Dampen;
  }
    /**
   * Returns the {@link VictorBase}, creating it if one does not exist.
   *
   * @return the {@link VictorBase}
   */
  public synchronized VictorBase getInstance() {
    if (instance == null) {
      instance = new VictorBase(this.victor, this.Dampen, this.name);
    }
    return instance;
  }
    /**
     * run victor
     */ 
    public void openLoop(double power) {
      power *= Dampen;
      victor.set(ControlMode.PercentOutput, power);
  }
    /**Dampens the open loop power
     * @param scaler decimal to scale the open loop power byu
     */
    public void setDampen(double scaler){
        Dampen = scaler;
    }

  /**
   * closePiston()
   */ 
  public void stop() {
      openLoop(0);
  }
    /**returns raw motor output */
    public double getRawOutput(){
      return this.victor.getMotorOutputPercent();
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
    String name = this.name;
    String cCom = name + " Current Command: ";
    String out = name + " Raw Output: ";
    String nCom = name + " No Command?: ";
    SmartDashboard.putString(cCom, getCurrentCommandName());
    SmartDashboard.putNumber(out, getRawOutput());
    SmartDashboard.putBoolean(nCom, noCommand());
  }
}
