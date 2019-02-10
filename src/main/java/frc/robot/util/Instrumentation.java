/**
 * Instrumentation Class that handles how telemetry from the Talon SRX interacts
 * with Driverstation and Smart Dashboard.
 */
package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Instrumentation {
	/* Tracking variables for instrumentation */
	private static int loops = 0;
	private static int timesInMotionMagic = 0;

	public static void Process(TalonSRX tal, StringBuilder sb) {
		/* Smart dash plots */
		SmartDashboard.putNumber("SensorVel", tal.getSelectedSensorVelocity(Constants.kPIDLoopIdx));
		SmartDashboard.putNumber("SensorPos", tal.getSelectedSensorPosition(Constants.kPIDLoopIdx));
		SmartDashboard.putNumber("MotorOutputPercent", tal.getMotorOutputPercent());
		SmartDashboard.putNumber("ClosedLoopError", tal.getClosedLoopError(Constants.kPIDLoopIdx));
		
		/* Check if Talon SRX is performing Motion Magic */
		if (tal.getControlMode() == ControlMode.MotionMagic) {
			++timesInMotionMagic;
		} else {
			timesInMotionMagic = 0;
		}

		if (timesInMotionMagic > 10) {
			/* Print the Active Trajectory Point Motion Magic is servoing towards */
			SmartDashboard.putNumber("ClosedLoopTarget", tal.getClosedLoopTarget(Constants.kPIDLoopIdx));
    		SmartDashboard.putNumber("ActTrajVelocity", tal.getActiveTrajectoryVelocity());
    		SmartDashboard.putNumber("ActTrajPosition", tal.getActiveTrajectoryPosition());
		}

		/* Periodically print to console */
		if (++loops >= 20) {
			loops = 0;
			System.out.println(sb.toString());
		}

		/* Reset created string for next loop */
		sb.setLength(0);
	}
}
