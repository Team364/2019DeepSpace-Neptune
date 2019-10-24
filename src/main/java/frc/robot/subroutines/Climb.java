package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;
import frc.robot.States;
import frc.robot.commands.ElevateToPosition;
import frc.robot.commands.EngageForeams;
import frc.robot.commands.FinalSequence;
import frc.robot.commands.LevitateToPosition;
import frc.robot.commands.WaitCommand;

public class Climb extends CommandGroup {

	public Climb(int climbSet) {
			addSequential(new ElevateToPosition(6));
			addSequential(new WaitCommand(1.6));
			addSequential(new EngageForeams(1.5));
			addSequential(new WaitCommand(0.5));
			addParallel(new ElevateToPosition(7));
			addSequential(new LevitateToPosition(RobotMap.lvl3Climb));
			addSequential(new WaitCommand(3.2));
			addSequential(new FinalSequence());
			addSequential(new WaitCommand(1));
			addSequential(new LevitateToPosition(0));

	}

	@Override
	protected void initialize() {
		States.led = States.LEDstates.CLIMBING;
	}

	@Override
	protected void interrupted() {
		States.led = States.LEDstates.PASSIVE;
	}
}
