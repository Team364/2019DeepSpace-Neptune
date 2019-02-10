package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.defaultcommands.LiftOpenLoop;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
// import com.ctre.phoenix.motorcontrol.can.VictorSPX;

// import frc.robot.RobotMap;
// import frc.robot.Robot;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.util.Constants;
import frc.robot.util.Instrumentation;

public class LiftSystem extends Subsystem {


    private TalonSRX leftLift;
    // private TalonSRX rightLift;

    /* Used to build string throughout loop */
    StringBuilder sb = new StringBuilder();
    /**Tracks the target position for instrumentation */
    private double TargetPosition;
    /**highest raw encoder count that the lift can reach */
    public double upperBound = 30000;
    /**lowest raw encoder count thtat the lift can reach */
    public double lowerBound = 0;
    /**Power that the lift is set to in Lift Open Loop */
    public double OpenLoopPower = 0;
    /**
     * LiftSystem()
     */ 
    public LiftSystem() {
        // //initialize talons and or victors here
        // // leftLift = new TalonSRX(RobotMap.leftLift);
        // // rightLift = new TalonSRX(RobotMap.rightLift);

        // // rightLift.follow(leftLift);
        // //TODO: See if it needs to be inverted
        // /* Factory default hardware to prevent unexpected behavior */
        // leftLift.configFactoryDefault();
        // //All followers will do the same

		// /* Configure Sensor Source for Pirmary PID */
		// leftLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
		// 									Constants.kPIDLoopIdx, 
		// 									Constants.kTimeoutMs);

		// /**
		//  * Configure Talon SRX Output and Sesnor direction accordingly
		//  * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		//  * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		//  */
		// leftLift.setSensorPhase(true);
		// leftLift.setInverted(false);

		// /* Set relevant frame periods to be at least as fast as periodic rate */
		// leftLift.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		// leftLift.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		// /* Set the peak and nominal outputs */
		// leftLift.configNominalOutputForward(0, Constants.kTimeoutMs);
        // leftLift.configNominalOutputReverse(0, Constants.kTimeoutMs);
        //TODO: Change these accordingly
		// leftLift.configPeakOutputForward(0.25, Constants.kTimeoutMs);
		// leftLift.configPeakOutputReverse(-0.25, Constants.kTimeoutMs);

		// /* Set Motion Magic gains in slot0 - see documentation */
		// leftLift.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		// leftLift.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
		// leftLift.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
		// leftLift.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
		// leftLift.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);

		// /* Set acceleration and vcruise velocity - see documentation */
		// leftLift.configMotionCruiseVelocity(3750, Constants.kTimeoutMs);
		// leftLift.configMotionAcceleration(1500, Constants.kTimeoutMs);

		// /* Zero the sensor */
		// leftLift.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
        
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new LiftOpenLoop());
    }
    public void openLoop(double power){
        // /*Deadband of 10% */
        // if(Math.abs(power) < 0.1){
        //     power = 0;
        // }
        //     power *= 0.3;
        //     leftLift.set(ControlMode.PercentOutput, power);
        //     OpenLoopPower = power;
    }
    /**
     * ElevateFirstStageToPosition
     */
    public void setLiftPosition(double Position){
        // System.out.println("The lift is moving to: " + Position);
        // leftLift.set(ControlMode.MotionMagic, Position);
        // TargetPosition = Position;
    }
    private double getTargetPosition(){
        // return TargetPosition;
        return 0;
    }
    public double getLiftError(){
        // return leftLift.getClosedLoopError();
        return 0;
    }
    /**
     * LiftReachedPosition
     * <p>Once the closed loop gets to about 200 error in raw encoder count it stops moving;
     * <p>therefore, once the velocity is zero position has been reached
     * @return if velocity is zero
     */
    public boolean reachedPosition(){
        // if(leftLift.getSelectedSensorVelocity() == 0){
        //     return true;
        // }else{
        //     return false;
        // }
        return false;
    }
    /**
     * @return velocity of the lift
     */
    public double getLiftVelocity(){
        // return leftLift.getSelectedSensorVelocity(Constants.kPIDLoopIdx);
        return 0;
    }

    /**
     * getLiftPosition()
     * @return Encoder count of lift
     */
    public double getLiftPosition(){
        // return leftLift.getSelectedSensorPosition(Constants.kPIDLoopIdx);
      return 0;
    }

    /**
     * Sets the lift Motor output to 0
     */
    public void stop(){
        // leftLift.set(ControlMode.PercentOutput, 0);
        // System.out.println("Lift Motors have stopped");
    }
    /**Run instrumentation */
    public void instrumentation(){
        
		// /* Get current Talon SRX motor output */
		// double motorOutput = leftLift.getMotorOutputPercent();
        // /* Prepare line to print */
        // sb.append("Lift:");
		// sb.append("\tOut%:");
		// sb.append(motorOutput);
		// sb.append("\tVel:");
		// sb.append(leftLift.getSelectedSensorVelocity(Constants.kPIDLoopIdx));
        // sb.append("\terr:");
        // sb.append(leftLift.getClosedLoopError(Constants.kPIDLoopIdx));
        // sb.append("\ttrg:");
        // sb.append(getTargetPosition());
        // Instrumentation.Process(leftLift, sb);
    }
    public void zero(){
        // leftLift.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    }
}
