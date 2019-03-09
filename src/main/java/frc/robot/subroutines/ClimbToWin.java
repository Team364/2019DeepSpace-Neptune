package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;
import frc.robot.commands.*;

public class ClimbToWin extends CommandGroup {

	public ClimbToWin() {
		addSequential(new ElevateToPosition(6));
		addSequential(new WaitCommand(1.6));
		addSequential(new EngageForeams(0.35));
		// addSequential(new SetPiston(Neptune.elevator.front, 1));
		addSequential(new WaitCommand(0.5));
		addSequential(new ElevateToPosition(7));
		// addSequential(new ClimbWithGyro(0, 3));
		// addSequential(new ClimbWithGyro(-4, 0.8));
		addSequential(new LevitateToPosition(RobotMap.climbLevitate));
		addSequential(new DriveClimberForwards());
		// addSequential(new LevitateUp());
	}

}
