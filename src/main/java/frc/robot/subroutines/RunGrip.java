package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commands.*;
import frc.robot.util.States;
import frc.robot.util.prefabs.commands.*;

/**
 * Subroutine to be run in teleop on button press
 */
public class RunGrip extends CommandGroup {

    private int set;
    
  public RunGrip(int set) {
    this.set = set;
    if(set == 1){ //Intake Cargo
    addSequential(new runIntake(0.5, true)); //Intake
    addSequential(new SetPiston(Robot.superStructure.claw, 1)); //Close Claw
    System.out.println("Intaking Cargo");
    }else if(set == 2){  //Intake Hatch
    addSequential(new SetPiston(Robot.superStructure.lever, 0)); //Open lever
    System.out.println("Grabbing Hatch");
    }else if(set == 3){ //Score Cargo
    addSequential(new runIntake(-0.75, false)); //Outtake
    addSequential(new SetPiston(Robot.superStructure.claw, 0)); //Open Claw
    System.out.println("Scoring Cargo");
    }else if(set == 4){  //Score Hatch
    addSequential(new SetPiston(Robot.superStructure.lever, 1)); //Close lever
    System.out.println("Scoring Hatch");
}
  }
}