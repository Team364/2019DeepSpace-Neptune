package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Neptune;
import frc.robot.commands.*;
import frc.robot.misc.commands.*;

/**
 * Subroutine to be run in teleop on button press
 */
public class RunGrip extends CommandGroup {
    
  public RunGrip(int set) {
    if(set == 1){ //Intake Cargo
    addSequential(new runIntake(-0.6, true)); //Intake
    addSequential(new CloseClaw()); //Close Claw
    System.out.println("Intaking Cargo");
    }else if(set == 2){  //Intake Hatch
    addSequential(new OpenLever()); //Open lever
    System.out.println("Grabbing Hatch");
    }else if(set == 3){ //Score Cargo
    addSequential(new runIntake(0.75, false)); //Outtake
    addSequential(new OpenClaw()); //Open Claw
    System.out.println("Scoring Cargo");
    }else if(set == 4){  //Score Hatch
    addSequential(new CloseLever()); //Close lever
    addSequential(new WaitCommand(0.1));
    addSequential(new OpenClaw());
    System.out.println("Scoring Hatch");
}
  }
}