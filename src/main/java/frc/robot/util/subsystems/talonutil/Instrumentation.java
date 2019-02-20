/**
 * Instrumentation Class that handles how telemetry from the Talon SRX interacts
 * with Driverstation and Smart Dashboard.
 */
package frc.robot.util.subsystems.talonutil;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.subsystems.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Instrumentation {
	/* Tracking variables for instrumentation
	of motion magic trajectories */
	private static int timesInMotionMagic = 0;

	public static void Process(TalonSRX tal) {
		
		/* Check if Talon SRX is performing Motion Magic */
		if (tal.getControlMode() == ControlMode.MotionMagic) {
			++timesInMotionMagic;
		} else {
			timesInMotionMagic = 0;
		}

		if (timesInMotionMagic > 10) {
			/* Print the Active Trajectory Point Motion Magic is servoing towards */
			SmartDashboard.putNumber("ClosedLoopTarget", tal.getClosedLoopTarget(TalonBase.PIDLoopIdx));
    		SmartDashboard.putNumber("ActTrajVelocity", tal.getActiveTrajectoryVelocity());
    		SmartDashboard.putNumber("ActTrajPosition", tal.getActiveTrajectoryPosition());
		}
	}
}
