package frc.robot.oi.controls;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.buttons.*;

public class JoystickTrigger extends Trigger{
    
    private double TriggerValue;

  public JoystickTrigger(int axisNumber) {
    TriggerValue = Robot.oi2.controller2.getRawAxis(axisNumber);
  } 

  public boolean pressed(){
      if(TriggerValue >= 0.5){
        return true;
      }else{
        return false;
      }
    
  }
  public boolean get(){
    return pressed();
}

  public void whenPressed(final Command command) {
    command.start();
  }

}

