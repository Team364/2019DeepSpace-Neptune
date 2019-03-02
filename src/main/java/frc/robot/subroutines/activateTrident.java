package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Neptune;
import frc.robot.commands.*;

public class ActivateTrident extends CommandGroup {

  public ActivateTrident(int set) {
    if (set == 1) { // Intake Cargo
      addSequential(new runIntake(-0.6, true)); // Intake
      addSequential(new SetPiston(Neptune.trident.claw, 0)); // Close Claw
      System.out.println("Intaking Cargo");
    } else if (set == 2) { // Intake Hatch
      addSequential(new SetPiston(Neptune.trident.lever, 1)); // Open lever
      System.out.println("Grabbing Hatch");
    } else if (set == 3) { // Score Cargo
      addSequential(new runIntake(0.75, false)); // Outtake
      addSequential(new SetPiston(Neptune.trident.claw, 1)); // Open Claw
      System.out.println("Scoring Cargo");
    } else if (set == 4) { // Score Hatch
      addParallel(new SetPiston(Neptune.trident.lever, 0)); // Close lever
      addSequential(new SetPiston(Neptune.trident.claw, 1));
      System.out.println("Scoring Hatch");
    }
  }
}