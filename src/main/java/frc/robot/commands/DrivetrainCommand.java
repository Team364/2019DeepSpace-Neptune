package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.SwerveMod;
import frc.robot.misc.math.Vector2;
import frc.robot.subsystems.SwerveMod;

import static frc.robot.RobotMap.*;

public class DrivetrainCommand extends Command {

	private final Drivetrain mDrivetrain;
	public int cycles;

	double forward;
	double strafe;
	double rotation;
	Vector2 translation;

	Vector2 lastTranslation;
	double lastRotation;

	public DrivetrainCommand(Drivetrain drivetrain) {
		mDrivetrain = drivetrain;
		cycles = 0;
		requires(drivetrain);
	}

	@Override
	protected void execute() {
        forward = -Neptune.oi.controller.getRawAxis(1);
		strafe = Neptune.oi.controller.getRawAxis(1);
		rotation = Neptune.oi.controller.getRawAxis(1);

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
		SmartDashboard.putNumber("TRANSLATION", translation.getAngle().toDegrees());
		//TODO: add field oriented boolean
			if (Math.abs(forward) > 0 || Math.abs(strafe) > 0 || Math.abs(rotation) > 0) {
				mDrivetrain.holonomicDrive(translation, rotation, true);
				lastTranslation = translation;
				lastRotation = rotation;
				cycles++;
			} else {
				if(cycles != 0){
				mDrivetrain.holonomicDrive(lastTranslation, lastRotation, true);
				}
			}		
		mDrivetrain.updateKinematics();
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
