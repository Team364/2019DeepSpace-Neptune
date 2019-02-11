package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.defaultcommands.Periodic;
import frc.robot.Robot;
import frc.robot.util.TalonBase;
import frc.robot.defaultcommands.testOpenLoop;

/**
 * Add your docs here.
 */
public class SuperStructure extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  /**tracks whehter or not the lift is in bounds for open loop control */
  //TODO: Get testSystem to work and then move lift and arm into here -- possibly move drive into here as well
  public boolean liftOutofBounds = false;
  public boolean armOutofBounds = false;
  public TalonBase testSystem;
  public TalonSRX testTalon;
  public SuperStructure(){
    testTalon = new TalonSRX(0);
    testSystem = new TalonBase(testTalon);
    testSystem.setDefaultCommand(new testOpenLoop());
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
     setDefaultCommand(new Periodic());
  }
  public void runTestTalon(){
    System.out.println("Test talon position: " + testSystem.getPosition());
  }
  
  public void resetEncoders(){
    Robot.liftSystem.zero();
    Robot.armSystem.zero();
  }
}
