package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;
import frc.robot.States;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;

public class Climb extends CommandGroup {

	private int level;
	public Climb(int level) {
		this.level = level;
		if(level == 3){
			addSequential(new ElevateToPosition(6));
			addSequential(new WaitCommand(1.6));
			addSequential(new EngageForeams(0.35));
			addSequential(new WaitCommand(0.5));
			addParallel(new ElevateToPosition(7));
			addSequential(new LevitateToPosition(RobotMap.climbLevitate));
			addSequential(new WaitCommand(4));
			addSequential(new FinalSequence(3));
		}else if(level == 2){
			addSequential(new ElevateToPosition(9));
			addSequential(new WaitCommand(1.6));
			addSequential(new EngageForeams(0.35));
			addSequential(new WaitCommand(0.5));
			addParallel(new ElevateToPosition(7));
			addSequential(new LevitateToPosition(11000));
			addSequential(new WaitCommand(2));
			addSequential(new FinalSequence(2));
		}
		
	}
	@Override
	protected void execute() {
		SmartDashboard.putNumber("Climb Per ", Neptune.climber.levitator.getMotorOutputPercent());
		SmartDashboard.putNumber("Climber Position", Neptune.climber.levitator.getSelectedSensorPosition());
		SmartDashboard.putNumber("ClimberVelocity", Neptune.climber.levitator.getSelectedSensorVelocity());
	}
	@Override
	protected void initialize() {
		System.out.println("Starting Climb");
		States.led = States.LEDstates.CLIMBING;
	}
	@Override
	protected void end() {
		System.out.println("Climb Finished");
	}
	@Override
	protected void interrupted() {
		System.out.println("Climb Stopped");
		States.led = States.LEDstates.PASSIVE;
	}
}
