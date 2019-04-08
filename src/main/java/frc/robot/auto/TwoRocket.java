package frc.robot.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subroutines.*;
import frc.robot.auto.commands.*;

public class TwoRocket extends CommandGroup {

  public TwoRocket() {
    setInterruptible(true);
    addSequential(new ActivateTrident(5));
    addSequential(new DriveToRocket(1, 0.5, 0.9, true));
    addSequential(new DriveToRocket(0.8, 0.88, 0.5, true));
    addSequential(new DriveToRocket(0.45, 0.55, 0.3, true));
    addSequential(new LimeAuto());
    addSequential(new ActivateTrident(4));
    addSequential(new DriveToRocket(-0.3, -0.3, 0.5, false));
  }
}
