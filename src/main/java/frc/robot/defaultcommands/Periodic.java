package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.States;
import frc.robot.util.prefabs.commands.*;
/**Controls state logic for variable robot funtionality */
public class Periodic extends Command {

  public int loops = 0;
  private boolean[] Limits;
  private boolean passiveLatch = false;
  private Command stopLift = new Stop(Robot.superStructure.lift);
  public static boolean manualControl;

  public Periodic() {
    requires(Robot.superStructure);
  }

  @Override
  protected void initialize() {
    Limits = Robot.superStructure.limitArray; 
  }

  @Override
  protected void execute() {

    //Update Limit Switches
      Limits[0] = Robot.superStructure.iL.get();//Ball

    if((Robot.superStructure.lift.getPosition() < 10000) &&(Robot.superStructure.lift.getPosition() > RobotMap.liftLowerBound)){
      States.liftZone = States.LiftZones.LOWER_DANGER;
    }else if((Robot.superStructure.lift.getPosition() > 100000)&&(Robot.superStructure.lift.getPosition() < RobotMap.liftUpperBound))
      States.liftZone = States.LiftZones.UPPER_DANGER;
    else{
      States.liftZone = States.LiftZones.SAFE;
    }
    //Encoder Upper Bound for Lift
    if((Robot.superStructure.lift.getPosition() >= RobotMap.liftUpperBound)){
      stopLift.start();
    }
    if((Robot.superStructure.lift.getPosition() <= RobotMap.liftLowerBound)){
      stopLift.start();
    }
    //Set the arm and lift back to start config
    if(Robot.superStructure.elevatorPassive() && !passiveLatch){
      System.out.println("Would move to neutral position");
      passiveLatch = Robot.superStructure.elevatorPassive();
    }
  }
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
