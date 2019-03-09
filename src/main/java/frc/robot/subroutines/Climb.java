package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;

public class Climb extends CommandGroup {

	public Climb() {
			addSequential(new ElevateToPosition(6));
			addSequential(new WaitCommand(1.6));
			addSequential(new EngageForeams(0.35));
			addSequential(new WaitCommand(0.5));
			addParallel(new ElevateToPosition(7));
			addSequential(new LevitateToPosition(RobotMap.climbLevitate));
			addSequential(new WaitCommand(4));
			addSequential(new DriveClimberForwards());
	
	}
	@Override
	protected void execute() {
		SmartDashboard.putNumber("Climb Per ", Neptune.climber.levitator.getMotorOutputPercent());
		SmartDashboard.putNumber("Climber Position", Neptune.climber.levitator.getSelectedSensorPosition());
	}
}
