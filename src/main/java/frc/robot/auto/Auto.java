package frc.robot.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subroutines.*;
import frc.robot.auto.commands.*;
import frc.robot.commands.SetIntakePos;

public class Auto extends CommandGroup {
  int auto;
  public Auto(int auto) {
    this.auto = auto;
    if(auto == 0){//Full driver control; auto from regionals
      addSequential(new ActivateTrident(5));
    }else if(auto == 1){//Cargo
      addSequential(new ActivateTrident(5));
      addSequential(new RampDrive(1, 1, 0.8));//Drive Forward
      addSequential(new LimeAuto());
      addSequential(new ActivateTrident(4));
      addSequential(new RampDrive(1, 1, 0.3));
      addSequential(new SetIntakePos());
    }else if(auto == 2){//Rocket
      addSequential(new ActivateTrident(5));
      addSequential(new RampDrive(2, 2, 0.3));//Drive off platform
      addSequential(new RampDrive(3, 0.8, 1));//Drive off platform
      addSequential(new RampDrive(0.4, 1.5, 1));
      addSequential(new RampDrive(1.25, 1.5, 0.3));
      addSequential(new LimeAuto());
      addSequential(new ActivateTrident(4));
      addSequential(new RampDrive(-1, -1, 0.3));
      addSequential(new SetIntakePos());
    }

 
  }
}
