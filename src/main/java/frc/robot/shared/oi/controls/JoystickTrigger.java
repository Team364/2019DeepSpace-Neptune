package frc.robot.shared.oi.controls;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.buttons.*;

public class JoystickTrigger extends Trigger{
    
    private double AxisValue;
    //Default Threshold
    private double Threshold = 0.5;

  public JoystickTrigger(int axisNumber) {
    AxisValue = Robot.oi2.controller2.getRawAxis(axisNumber);
  } 

  public void setTheshold(double newThreshold){
    Threshold = newThreshold;
  }


  public boolean pressed(){
      if(AxisValue >= Threshold){
        return true;
      }else{
        return false;
      }
    
  }
  public boolean get(){
    return pressed();
}


}

