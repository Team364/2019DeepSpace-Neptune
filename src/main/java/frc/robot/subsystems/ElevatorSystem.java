package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.util.prefabs.subsystems.*;
import frc.robot.defaultcommands.DriveOpenLoop;
/**
 * Add your docs here.
 */
public class ElevatorSystem extends Subsystem {

  private TalonBase arm;
  private TalonBase lift;
  
  public ElevatorSystem(TalonBase lift, TalonBase arm){
    this.lift = lift;
    this.arm = arm;
  } 
  public void elevateTo(double liftHeight, double armAngle){
    lift.MoveToPosition(liftHeight);
    arm.MoveToPosition(armAngle);
  }

  @Override
  public void initDefaultCommand() {
  }
}
