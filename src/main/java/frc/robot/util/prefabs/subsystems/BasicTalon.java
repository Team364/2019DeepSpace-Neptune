/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util.prefabs.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * Add your docs here.
 */
public class BasicTalon extends Subsystem {

  private TalonSRX talon;
  private double Dampen;
  private String name;
  private static BasicTalon instance;
  
  public BasicTalon(TalonSRX talon, double Dampen, String name){
    this.talon = talon;
    this.Dampen = Dampen;
    this.name = name;
    talon.configFactoryDefault();
  }
  /**
   * Returns the {@link BasicTalon}, creating it if one does not exist.
   *
   * @return the {@link BasicTalon}
   */
  public synchronized BasicTalon getInstance() {
    if (instance == null) {
      instance = new BasicTalon(this.talon, this.Dampen, this.name);
    }
    return instance;
  }

      /**Open loop is to run in the default command */
      public void openLoop(double power){
        /*Deadband of 10% */
        if(Math.abs(power) < 0.1){
            power = 0;
        }
            power *= Dampen;
            this.talon.set(ControlMode.PercentOutput, power);
    }
    /**Dampens the open loop power
     * @param scaler decimal to scale the open loop power byu
     */
    public void setDampen(double scaler){
        Dampen = scaler;
    }
    /**
     * Sets the talon Motor output to 0
     */
    public void stop(){
        this.talon.set(ControlMode.PercentOutput, 0);
    }
    public boolean noCommand(){
      if(this.getCurrentCommandName() == ""){
        return true;
      }else{
        return false;
      }
    }

    public void postSmartDashVars(){
      String mOut = name + " Motor Output: ";
      String cCome = name + " Current Command: ";
      String cNam = this.getCurrentCommandName();
      SmartDashboard.putNumber(mOut, this.talon.getMotorOutputPercent());
      SmartDashboard.putString(cCome, this.getCurrentCommandName());
      SmartDashboard.putBoolean("Empty Command", noCommand());
    }
  @Override
  public void initDefaultCommand() {
  }
}
