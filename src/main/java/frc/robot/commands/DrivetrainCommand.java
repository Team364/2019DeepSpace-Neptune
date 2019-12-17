package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.subsystems.DriveTrain;
import frc.robot.misc.math.Vector2;

import static frc.robot.RobotMap.*;

public class DrivetrainCommand extends Command {

	private final DriveTrain mDrivetrain;
	public int cycles;

	double forward;
	double strafe;
	double rotation;
	private Vector2 translation;

	Vector2 lastTranslation;
	double lastRotation;
	double plzStop = 0;

	public DrivetrainCommand(DriveTrain drivetrain) {
		mDrivetrain = drivetrain;
		cycles = 0;
		requires(drivetrain);
	}
	@Override
	protected void initialize(){

	}

	@Override
	protected void execute() {
        forward = -Neptune.oi.controller.getRawAxis(1);
		strafe = Neptune.oi.controller.getRawAxis(0);
		rotation = Neptune.oi.controller.getRawAxis(4);
		boolean zeroPoint = false;
		if(zeroPoint){
			translation = new Vector2(-1, 0);
		}
		else{
			translation = new Vector2(forward, strafe);
		}
		
			if (Math.abs(forward) > STICKDEADBAND || Math.abs(strafe) > STICKDEADBAND || Math.abs(rotation) > STICKDEADBAND) {
				mDrivetrain.holonomicDrive(translation, rotation, !zeroPoint);
				lastTranslation = translation;
				lastRotation = rotation;
				cycles++;

			} else {
				if(cycles != 0){

					mDrivetrain.holonomicDrive(lastTranslation, lastRotation, false);
				}
			}	
			if(cycles != 0){
			mDrivetrain.updateKinematics();
			}
	}


	@Override
	protected void end() {
		mDrivetrain.stopDriveMotors();
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
