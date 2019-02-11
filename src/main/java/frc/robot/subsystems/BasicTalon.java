/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * Add your docs here.
 */
public class BasicTalon extends Subsystem {

  private TalonSRX talon;
  private double Dampen;

  public BasicTalon(TalonSRX talon, double Dampen){
    this.talon = talon;
    this.Dampen = Dampen;
    talon.configFactoryDefault();
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
        System.out.println(talon + " has stopped");
    }

  @Override
  public void initDefaultCommand() {
  }
}
