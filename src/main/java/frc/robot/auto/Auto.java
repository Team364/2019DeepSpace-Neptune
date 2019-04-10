package frc.robot.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subroutines.*;

public class Auto extends CommandGroup {
  int auto;
  public Auto(int auto) {
    this.auto = auto;
    if(auto == 0){//Full driver control; auto from regionals
      addSequential(new ActivateTrident(5));
    }

 
  }
}
