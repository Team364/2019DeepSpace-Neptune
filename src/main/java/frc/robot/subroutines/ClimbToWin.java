package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Neptune;
import frc.robot.commands.*;

public class ClimbToWin extends CommandGroup {
  
	public ClimbToWin() {
		addSequential(new ElevateToPosition(6));
		addSequential(new WaitCommand(2));
		addSequential(new SetPiston(Neptune.elevator.front, 1));
		addSequential(new WaitCommand(2));
		addParallel(new ElevateToPosition(7));
		addSequential(new ClimbWithGyro(0));
		addSequential(new ClimbWithGyro(-5));
		addSequential(new DriveClimberForwards());
	}

}
