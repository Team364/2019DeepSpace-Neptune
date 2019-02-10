package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.defaultcommands.LiftOpenLoop;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.RobotMap;
import frc.robot.util.PIDCalc;
import frc.robot.Robot;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LiftSystem extends Subsystem {


    private TalonSRX rightLift;
    // private TalonSRX leftLift;

    // public PIDCalc pidLift;
    // public double pidLiftOutput;
    /* Used to build string throughout loop */
    StringBuilder sb = new StringBuilder();
    private double TargetPosition;
    /**
     * LiftSystem()
     */ 
    public LiftSystem() {
        //initialize talons and or victors here
        // pidLift = new PIDCalc(0.01, 0, 0, 0, "PidLift");
        rightLift = new TalonSRX(RobotMap.rightLift);
        // leftLift = new TalonSRX(RobotMap.leftLift);

        // rightLift.follow(leftLift);
        		/* Factory default hardware to prevent unexpected behavior */
		rightLift.configFactoryDefault();

		/* Configure Sensor Source for Pirmary PID */
		rightLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
											Robot.constants.kPIDLoopIdx, 
											Robot.constants.kTimeoutMs);

		/**
		 * Configure Talon SRX Output and Sesnor direction accordingly
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		 */
		rightLift.setSensorPhase(true);
		rightLift.setInverted(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		rightLift.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Robot.constants.kTimeoutMs);
		rightLift.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Robot.constants.kTimeoutMs);

		/* Set the peak and nominal outputs */
		rightLift.configNominalOutputForward(0, Robot.constants.kTimeoutMs);
		rightLift.configNominalOutputReverse(0, Robot.constants.kTimeoutMs);
		rightLift.configPeakOutputForward(0.25, Robot.constants.kTimeoutMs);
		rightLift.configPeakOutputReverse(-0.25, Robot.constants.kTimeoutMs);

		/* Set Motion Magic gains in slot0 - see documentation */
		rightLift.selectProfileSlot(Robot.constants.kSlotIdx, Robot.constants.kPIDLoopIdx);
		rightLift.config_kF(Robot.constants.kSlotIdx, Robot.constants.kGains.kF, Robot.constants.kTimeoutMs);
		rightLift.config_kP(Robot.constants.kSlotIdx, Robot.constants.kGains.kP, Robot.constants.kTimeoutMs);
		rightLift.config_kI(Robot.constants.kSlotIdx, Robot.constants.kGains.kI, Robot.constants.kTimeoutMs);
		rightLift.config_kD(Robot.constants.kSlotIdx, Robot.constants.kGains.kD, Robot.constants.kTimeoutMs);

		/* Set acceleration and vcruise velocity - see documentation */
		rightLift.configMotionCruiseVelocity(3750, Robot.constants.kTimeoutMs);
		rightLift.configMotionAcceleration(1500, Robot.constants.kTimeoutMs);

		/* Zero the sensor */
		rightLift.setSelectedSensorPosition(0, Robot.constants.kPIDLoopIdx, Robot.constants.kTimeoutMs);
        
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new LiftOpenLoop());
    }
    public void openLoop(double power){
        //TODO: add lift encoder count soft limit here; if then command that won't allow this to be run
        //if the lift is beyond a certain encoder count
        // leftLift.set(ControlMode.PercentOutput, power);
        if(power > 0.2){
            System.out.println("The lift is moving at: " + power);
        }
       
    }
    //TODO: Move to command
    /**
     * ElevateFirstStageToPosition
     */
    public void setLiftPosition(double Position){
        // pidLiftOutput = pidLift.calculateOutput(Position, getLiftPosition());
        // leftLift.set(ControlMode.PercentOutput, pidLiftOutput);
        // SmartDashboard.putNumber("PidLiftFirstStageOutput", pidLiftOutput);
        // SmartDashboard.putBoolean("firstStageReachHeading", reachedPosition(Position));
        System.out.println("The lift is moving to: " + Position);
        rightLift.set(ControlMode.MotionMagic, Position);
        TargetPosition = Position;
    }
    private double getTargetPosition(){
        return TargetPosition;
    }

    /**
     * LiftReachedPosition
     * @param position
     * @return if Encoder counts for first stage are in range of a 100 count threshold
     */
    public boolean reachedPosition(double position){
        // double threshold = 100;
        // //Half of Threshold Value
        // double hThreshold = threshold/2;
        // return (getLiftPosition() <= (position + hThreshold) && getLiftPosition() >= (position - hThreshold));
        //Here only as placeholder. Delete whenever orignal code is uncommented
        return false;
    }

    /**
     * getLiftPosition()
     * @return Encoder count of lift
     */
    public double getLiftPosition(){
        // return leftLift.getSelectedSensorPosition(0);
        //Here only as placeholder. Delete whenever orignal code is uncommented
        return 0;
    }

    /**
     * Sets the lift Motor output to 0
     */
    public void stop(){
        // leftLift.set(ControlMode.PercentOutput, 0);
        System.out.println("Lift Motors have stopped");
    }
    /**Run instrumentation */
    public void instrumentation(){
        
		/* Get current Talon SRX motor output */
		double motorOutput = rightLift.getMotorOutputPercent();
        /* Prepare line to print */
		sb.append("\tOut%:");
		sb.append(motorOutput);
		sb.append("\tVel:");
		sb.append(rightLift.getSelectedSensorVelocity(Robot.constants.kPIDLoopIdx));
        sb.append("\terr:");
        sb.append(rightLift.getClosedLoopError(Robot.constants.kPIDLoopIdx));
        sb.append("\ttrg:");
        sb.append(getTargetPosition());
        Robot.instrumentation.Process(rightLift, sb);
    }
}
