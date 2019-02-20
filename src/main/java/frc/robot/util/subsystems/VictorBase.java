package frc.robot.util.subsystems;

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
  private Double MaxForward;
  private Double MaxBackward;
  private Double MinForward;
  private Double MinBackward;
  private VictorBase instance;

  public VictorBase(
    VictorSPX victor, 
    double MaxForward, 
    double MaxBackward, 
    double MinForward, 
    double MinBackward, 
    String name){
    this.victor = victor;
    this.name = name;
    this.MaxForward = MaxForward;
    this.MaxBackward = MaxBackward;
    this.MinForward = MinForward;
    this.MinBackward = MinBackward;
    victor.configPeakOutputForward(MaxForward);
    victor.configPeakOutputReverse(MaxBackward);
    victor.configNominalOutputReverse(MinBackward);
    victor.configNominalOutputForward(MinForward);
  }
    /**
     * run victor
     */ 
    public void openLoop(double power) {
      victor.set(ControlMode.PercentOutput, power);
  }
  /**
   * stop Victor()
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
   /**Sets the lowest speed the trajectory can run at in the forward direction */
   public void setNominalOutputForward(double percentOutput){
    victor.configNominalOutputForward(percentOutput);
    }
    /**Sets the lowest speed the trajectory can run at in the reverse direction */
    public void setNominalOutputReverse(double percentOutput){
        victor.configNominalOutputReverse(percentOutput);
    }
    /**Sets the hightest speed the trajectory can run at in the forward direction */
    public void setPeakOutputForward(double percentOutput){
        victor.configPeakOutputForward(percentOutput);
    }
    /**Sets the hightest speed the trajectory can run at in the reverse direction */
    public void setPeakOutputReverse(double percentOutput){
        victor.configPeakOutputReverse(percentOutput);
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
