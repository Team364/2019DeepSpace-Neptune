package frc.robot.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subroutines.*;
import frc.robot.auto.commands.*;
import frc.robot.commands.SetIntakePos;

public class OneCargoShip extends CommandGroup {

  public OneCargoShip() {
    //setInterruptible(true);
    addSequential(new ActivateTrident(5));
    addSequential(new RampDrive(2, 2, 0.8));
    addSequential(new LimeAuto());
    addSequential(new ActivateTrident(4));
    addSequential(new RampDrive(-1, -1, 0.3));
    addSequential(new SetIntakePos());
 
  }
}
