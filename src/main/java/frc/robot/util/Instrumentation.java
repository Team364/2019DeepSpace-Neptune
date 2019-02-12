/**
 * Instrumentation Class that handles how telemetry from the Talon SRX interacts
 * with Driverstation and Smart Dashboard.
 */
package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.talons.ComplexTalon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Instrumentation {
	/* Tracking variables for instrumentation
	of motion magic trajectories */
	private static int loops = 0;
	private static int timesInMotionMagic = 0;

	public static void Process(TalonSRX tal, StringBuilder sb) {
		
		/* Check if Talon SRX is performing Motion Magic */
		if (tal.getControlMode() == ControlMode.MotionMagic) {
			++timesInMotionMagic;
		} else {
			timesInMotionMagic = 0;
		}

		if (timesInMotionMagic > 10) {
			/* Print the Active Trajectory Point Motion Magic is servoing towards */
			SmartDashboard.putNumber("ClosedLoopTarget", tal.getClosedLoopTarget(ComplexTalon.PIDLoopIdx));
    		SmartDashboard.putNumber("ActTrajVelocity", tal.getActiveTrajectoryVelocity());
    		SmartDashboard.putNumber("ActTrajPosition", tal.getActiveTrajectoryPosition());
		}
	}
}
