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


    private TalonSRX leftLift;
    // private TalonSRX rightLift;

    // public PIDCalc pidLift;
    // public double pidLiftOutput;
    /* Used to build string throughout loop */
    StringBuilder sb = new StringBuilder();
    private double TargetPosition;
    /**highest raw encoder count that the lift can reach */
    public double upperBound = 30000;
    /**lowest raw encoder count thtat the lift can reach */
    public double lowerBound = 0;
    /**
     * LiftSystem()
     */ 
    public LiftSystem() {
        //initialize talons and or victors here
        // pidLift = new PIDCalc(0.01, 0, 0, 0, "PidLift");
        // leftLift = new TalonSRX(RobotMap.leftLift);
        // rightLift = new TalonSRX(RobotMap.rightLift);
        /*Slave the right to the left. Do not forget this
        Possibly negate one of the talons as well
        use open loop to test the lift first as to minimize
        damage in the event that negation is required.*/
        leftLift = new TalonSRX(10);

        // leftLift.follow(leftLift);
        		/* Factory default hardware to prevent unexpected behavior */
		leftLift.configFactoryDefault();

		/* Configure Sensor Source for Pirmary PID */
		leftLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
											Robot.constants.kPIDLoopIdx, 
											Robot.constants.kTimeoutMs);

		/**
		 * Configure Talon SRX Output and Sesnor direction accordingly
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		 */
		leftLift.setSensorPhase(true);
		leftLift.setInverted(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		leftLift.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Robot.constants.kTimeoutMs);
		leftLift.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Robot.constants.kTimeoutMs);

		/* Set the peak and nominal outputs */
		leftLift.configNominalOutputForward(0, Robot.constants.kTimeoutMs);
		leftLift.configNominalOutputReverse(0, Robot.constants.kTimeoutMs);
		leftLift.configPeakOutputForward(0.25, Robot.constants.kTimeoutMs);
		leftLift.configPeakOutputReverse(-0.25, Robot.constants.kTimeoutMs);

		/* Set Motion Magic gains in slot0 - see documentation */
		leftLift.selectProfileSlot(Robot.constants.kSlotIdx, Robot.constants.kPIDLoopIdx);
		leftLift.config_kF(Robot.constants.kSlotIdx, Robot.constants.kGains.kF, Robot.constants.kTimeoutMs);
		leftLift.config_kP(Robot.constants.kSlotIdx, Robot.constants.kGains.kP, Robot.constants.kTimeoutMs);
		leftLift.config_kI(Robot.constants.kSlotIdx, Robot.constants.kGains.kI, Robot.constants.kTimeoutMs);
		leftLift.config_kD(Robot.constants.kSlotIdx, Robot.constants.kGains.kD, Robot.constants.kTimeoutMs);

		/* Set acceleration and vcruise velocity - see documentation */
		leftLift.configMotionCruiseVelocity(3750, Robot.constants.kTimeoutMs);
		leftLift.configMotionAcceleration(1500, Robot.constants.kTimeoutMs);

		/* Zero the sensor */
		leftLift.setSelectedSensorPosition(0, Robot.constants.kPIDLoopIdx, Robot.constants.kTimeoutMs);
        
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new LiftOpenLoop());
    }
    public void openLoop(double power){
        //TODO: add lift encoder count soft limit here; if then command that won't allow this to be run
        //if the lift is beyond a certain encoder count
        // leftLift.set(ControlMode.PercentOutput, power);
        if(Math.abs(power) < 0.1){
            power = 0;
        }
            System.out.println("The lift is moving at: " + power);
            power *= 0.3;
            leftLift.set(ControlMode.PercentOutput, power);
        
       
    }
    //TODO: Move to command
    /**
     * ElevateFirstStageToPosition
     */
    public void setLiftPosition(double Position){
        System.out.println("The lift is moving to: " + Position);
        leftLift.set(ControlMode.MotionMagic, Position);
        TargetPosition = Position;
    }
    private double getTargetPosition(){
        return TargetPosition;
    }
    public double getLiftError(){
        return leftLift.getClosedLoopError();
    }
    /**
     * LiftReachedPosition
     * @return if Encoder counts for first stage are in range of a 100 count threshold
     */
    public boolean reachedPosition(){
        if(leftLift.getSelectedSensorVelocity() == 0){
            return true;
        }else{
            return false;
        }
    }
    public double getLiftVelocity(){
        return leftLift.getSelectedSensorVelocity(0);
    }

    /**
     * getLiftPosition()
     * @return Encoder count of lift
     */
    public double getLiftPosition(){
        return leftLift.getSelectedSensorPosition(0);
      
    }

    /**
     * Sets the lift Motor output to 0
     */
    public void stop(){
        leftLift.set(ControlMode.PercentOutput, 0);
        System.out.println("Lift Motors have stopped");
    }
    /**Run instrumentation */
    public void instrumentation(){
        
		/* Get current Talon SRX motor output */
		double motorOutput = leftLift.getMotorOutputPercent();
        /* Prepare line to print */
		sb.append("\tOut%:");
		sb.append(motorOutput);
		sb.append("\tVel:");
		sb.append(leftLift.getSelectedSensorVelocity(Robot.constants.kPIDLoopIdx));
        sb.append("\terr:");
        sb.append(leftLift.getClosedLoopError(Robot.constants.kPIDLoopIdx));
        sb.append("\ttrg:");
        sb.append(getTargetPosition());
        Robot.instrumentation.Process(leftLift, sb);
    }
    public void zeroLiftCounts(){
        leftLift.setSelectedSensorPosition(0, Robot.constants.kPIDLoopIdx, Robot.constants.kTimeoutMs);
    }
}
