package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;
import frc.robot.subsystems.Drivetrain;

import static frc.robot.RobotMap.*;

public class DrivetrainCommand extends Command {

	private final Drivetrain mDrivetrain;

	public DrivetrainCommand(Drivetrain drivetrain) {
		mDrivetrain = drivetrain;
		requires(drivetrain);
	}

	@Override
	protected void execute() {
        double forward = -Neptune.oi.controller.getRawAxis()
		double strafe = Neptune.oi.controller.getRawAxis();
		double rotation = Robot.oi.rotation();

		forward *= Math.abs(forward);
		strafe *= Math.abs(strafe);
		rotation *= Math.abs(rotation);

		forward = deadband(forward);
		strafe = deadband(strafe);
		rotation = deadband(rotation);

		SmartDashboard.putNumber("Forward", forward);
		SmartDashboard.putNumber("Strafe", strafe);
		SmartDashboard.putNumber("Rotation", rotation);

		mDrivetrain.holonomicDrive(forward, strafe, rotation);
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
