package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.defaultcommands.Periodic;
import frc.robot.Robot;
import frc.robot.util.OpenLoop;
import frc.robot.util.TalonBase;
import frc.robot.defaultcommands.testOpenLoop;

/**
 * Add your docs here.
 */
public class SuperStructure extends Subsystem {
  //TLDR: The subsystem code is SUPER repetitve and it wouldn't hurt to reduce the code to
  //instances of template classes and running methods in here because all the important custom
  //stuff is run in commands

  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  /**tracks whehter or not the lift is in bounds for open loop control */
  //TODO: Get testSystem to work and then move lift and arm into here -- possibly move drive into here as well
  //Move NavX into here as well
  //Really all the specific things are done in commands, so having duplicate subsystem files seem redundant.
  //Would be easier to follow if everything was delegated from a superstructure instead
  //Grip and climb can be there own commands perhaps
  //Grip system may not need its own system either
  //Children of superstructure can be required so that other commands cannot access them
  //But arm, lift, and drive are going to be redundant.
  //Auto will mostly be running closed loop stuff with commands
  //Methods aren't really needed and so using TalonBases won't be an issue at all
  //Theres probably only going to be the superstructure subsystem class and maybe vision
  //But honestly those methods can be placed in here too
  public boolean liftOutofBounds = false;
  public boolean armOutofBounds = false;
  public TalonBase testSystem;
  public TalonSRX testTalon;
  public VictorSPX testSlave;
  public SuperStructure(){
    testTalon = new TalonSRX(0);
    testSlave = new VictorSPX(2);
    testSystem = new TalonBase(testTalon, 0, 0, 0.25, -0.25, 3750, 1500, false, 0, 0, 0.4);
    testSystem.setDefaultCommand(new OpenLoop(testSystem, 0, 0.1, false, 0.0, 0.0));
    testSlave.follow(testTalon);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
     setDefaultCommand(new Periodic());
  }
  public void runTestTalon(){
    System.out.println("Test talon position: " + testSystem.getPosition());
  }
  public void driveOpenLoop(double right, double left){
  //Possibly use seperate talonBase objects for each side of the drive train
  }
  public void resetEncoders(){
    Robot.liftSystem.zero();
    Robot.armSystem.zero();
  }
  public void postImplementation(){
    //Run all the implementations
  }
}
