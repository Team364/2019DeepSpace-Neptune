package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;
import frc.robot.States;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;

public class Climb extends CommandGroup {

	private int climbSet;
	public Climb(int climbSet) {
		this.climbSet = climbSet;
		if(climbSet == 3){//Level 3 climb from platform
			addSequential(new ElevateToPosition(6));
			addSequential(new WaitCommand(1.6));
			addSequential(new EngageForeams(0.35));
			addSequential(new WaitCommand(0.5));
			addParallel(new ElevateToPosition(7));
			addSequential(new LevitateToPosition(RobotMap.lvl3Climb));
			addSequential(new WaitCommand(3.2));
			addSequential(new FinalSequence(3));
		}else if(climbSet == 2){//Level 2 climb from platform
			addSequential(new ElevateToPosition(9));
			addSequential(new WaitCommand(1.2));
			addSequential(new EngageForeams(0.3));
			addSequential(new WaitCommand(0.2));
			addParallel(new ElevateToPosition(7));
			addSequential(new LevitateToPosition(RobotMap.lvl2Climb));
			addSequential(new WaitCommand(1));
			addSequential(new FinalSequence(2));
		}
		// }else if(climbSet == 1){//Just lift up
		// 	addSequential(new ElevateToPosition(10));
		// }else if(climbSet == 4){
		// 	addParallel(new ElevateToPosition(7));
		// 	addSequential(new LevitateToPosition(RobotMap.intermediateClimb));
		// }
		
	}
	@Override
	protected void execute() {
		SmartDashboard.putNumber("Climb Per ", Neptune.climber.levitator.getMotorOutputPercent());
		SmartDashboard.putNumber("Climber Position", Neptune.climber.levitator.getSelectedSensorPosition());
		SmartDashboard.putNumber("ClimberVelocity", Neptune.climber.levitator.getSelectedSensorVelocity());
		if(climbSet == 4){
			Neptune.climbDrive = true;
		}
	}
	@Override
	protected void initialize() {
		System.out.println("Starting Climb for climbSet: " + climbSet);
		States.led = States.LEDstates.CLIMBING;
	}
	@Override
	protected void end() {
		System.out.println("Climb Finished");
	}
	// @Override
	// protected boolean isFinished() {
	// 	return !Neptune.endGame;
	// }
	@Override
	protected void interrupted() {
		System.out.println("Climb Stopped");
		if(!Neptune.endGame){
			System.out.println("Cannot climb before endgame");
		}
		States.led = States.LEDstates.PASSIVE;
	}
}
