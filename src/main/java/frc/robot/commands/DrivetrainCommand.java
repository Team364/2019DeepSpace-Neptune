package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.SwerveMod;
import frc.robot.misc.math.Vector2;
import frc.robot.subsystems.SwerveMod;

import static frc.robot.RobotMap.*;
import static frc.robot.subsystems.Drivetrain.*;

public class DrivetrainCommand extends Command {

	private final Drivetrain mDrivetrain;

	double forward;
	double strafe;
	double rotation;
	Vector2 translation;

	Vector2 lastTranslation;
	double lastRotation;

	public DrivetrainCommand(Drivetrain drivetrain) {
		mDrivetrain = drivetrain;
		requires(drivetrain);
	}

	@Override
	protected void execute() {
        forward = -Neptune.oi.controller.getRawAxis(1);
		strafe = Neptune.oi.controller.getRawAxis(0);
		rotation = Neptune.oi.controller.getRawAxis(4);

		forward *= Math.abs(forward);
		strafe *= Math.abs(strafe);
		rotation *= Math.abs(rotation);

		forward = deadband(forward);
		strafe = deadband(strafe);
		rotation = deadband(rotation);
		translation = new Vector2(forward, strafe);

		SmartDashboard.putNumber("Forward", forward);
		SmartDashboard.putNumber("Strafe", strafe);
		SmartDashboard.putNumber("Rotation", rotation);

		//TODO: add field oriented boolean
		for (SwerveMod mod : mDrivetrain.getSwerveModules()) {
			if (forward > 0 || strafe > 0 || rotation > 0) {
				mDrivetrain.holonomicDrive(translation, rotation, true);
				lastTranslation = translation;
				lastRotation = rotation;
				cycles++;
			} else {
				if(cycles != 0){
					mDrivetrain.holonomicDrive(lastTranslation, lastRotation, true);
				}
			}
		}	
		if(cycles != 0){	
			mDrivetrain.updateKinematics();
		}
		else{
			for(SwerveMod mod : Neptune.driveTrain.getSwerveModules()){
                mod.setZero();
            }
		}
	}

	private double deadband(double input) {
		if (Math.abs(input) < STICKDEADBAND) return 0;
		return input;
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
