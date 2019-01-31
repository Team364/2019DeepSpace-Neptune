package frc.robot.oi.controls;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.buttons.*;

public class JoystickPOV extends Trigger{
    
    private double desiredDegree;
    private double actualDegree;

  public JoystickPOV(int degree) {
    actualDegree = Robot.oi2.controller2.getPOV();
    desiredDegree = degree;
  } 
  

  public boolean pressed(){
      if(desiredDegree >= actualDegree){
        return true;
      }else{
        return false;
      }
    
  }
  public boolean get(){
    return pressed();
}
}

