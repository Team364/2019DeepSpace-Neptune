package frc.robot.util;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
// import com.ctre.phoenix.motorcontrol.can.VictorSPX;

// import frc.robot.RobotMap;
// import frc.robot.Robot;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.util.Constants;
import frc.robot.util.Instrumentation;

public class TalonBase extends Subsystem {
    /**highest raw encoder count that the talon can reach */
    public double upperBound = 30000;
    /**lowest raw encoder count that the talon can reach */
    public double lowerBound = 0;
    /**Power that the talon is set to in Open Loop */
    public double openLoopPower = 0;
    /**Default Command */
    public Command defaultCommand;

    /**Talon */
    private TalonSRX talon;
    /* Used to build string throughout loop */
    StringBuilder sb = new StringBuilder();
    /**Tracks the target position for instrumentation */
    private double TargetPosition;
    /**Dampening variable to scale down the motor output in open loop 
     * <p>if not set, it will be 1, which leads to no affect*/
    private double Dampen = 1.0;
    /**Talon Cruise velocity maximum for motion magic closed loop control
     * <p>Sensor units per 100ms - 4096 sensor units per revolution*/
    private int cruiseVelocity = 3750;
    /**Talon Acceleration for motion magic closed loop control
     * <p>Sensor units per 100ms - 4096 sensor units per revolution*/
    private int acceleration = 1500;
    /**lowest speed the trajectory can run at in the forward direction */
    private double nominalOutputForward = 0;
    /**lowest speed the trajectory can run at in the reverse direction */
    private double nominalOutputReverse = 0;
    /**highest speed the trajectory can run at in the forward direction */
    private double peakOutputForward = 0.25;
    /**highest speed the trajectory can run at in the reverse direction */
    private double peakOutputReverse = -0.25;
    /**
     * TalonBase()
     */ 
    public TalonBase(TalonSRX talon) {
        this.talon = talon;
        /* Factory default hardware to prevent unexpected behavior */
        talon.configFactoryDefault();
        //All followers will do the same

		/* Configure Sensor Source for Pirmary PID */
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
											Constants.kPIDLoopIdx, 
											Constants.kTimeoutMs);

		/**
		 * Configure Talon SRX Output and Sesnor direction accordingly
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		 */
		talon.setSensorPhase(true);
		talon.setInverted(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		/* Set the peak and nominal outputs */
		talon.configNominalOutputForward(nominalOutputForward, Constants.kTimeoutMs);
        talon.configNominalOutputReverse(nominalOutputReverse, Constants.kTimeoutMs);
		talon.configPeakOutputForward(peakOutputForward, Constants.kTimeoutMs);
		talon.configPeakOutputReverse(peakOutputReverse, Constants.kTimeoutMs);

		/* Set Motion Magic gains in slot0 - see documentation */
		talon.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		talon.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
		talon.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
		talon.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
		talon.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);

		/* Set acceleration and vcruise velocity - see documentation */
		talon.configMotionCruiseVelocity(cruiseVelocity, Constants.kTimeoutMs);
		talon.configMotionAcceleration(acceleration, Constants.kTimeoutMs);

		/* Zero the sensor */
		talon.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
        
    }

    /**Sets the lowest speed the trajectory can run at in the forward direction */
    public void setNominalOutputForward(double percentOutput){
        talon.configNominalOutputForward(percentOutput);
    }
    /**Sets the lowest speed the trajectory can run at in the reverse direction */
    public void setNominalOutputReverse(double percentOutput){
        talon.configNominalOutputReverse(percentOutput);
    }
    /**Sets the hightest speed the trajectory can run at in the forward direction */
    public void setPeakOutputForward(double percentOutput){
        talon.configPeakOutputForward(percentOutput);
    }
    /**Sets the hightest speed the trajectory can run at in the reverse direction */
    public void setPeakOutputReverse(double percentOutput){
        talon.configPeakOutputReverse(percentOutput);
    }

    /**Open loop is to run in the default command */
    public void openLoop(double power){
        /*Deadband of 10% */
        if(Math.abs(power) < 0.1){
            power = 0;
        }
            power *= Dampen;
            this.talon.set(ControlMode.PercentOutput, power);
            openLoopPower = power;
    }
    /**Dampens the open loop power
     * @param scaler decimal to scale the open loop power byu
     */
    public void setDampen(double scaler){
        Dampen = scaler;
    }
    /**
     * Sets the talon Motor output to 0
     */
    public void stop(){
        this.talon.set(ControlMode.PercentOutput, 0);
        System.out.println(talon + " has stopped");
    }
    /**Sets the encoder value to zero */
    public void zero(){
        talon.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
   }
    /**
     * MoveToPosition Closed Loop
     */
    public void MoveToPosition(double Position){
        System.out.println("The talon is moving to: " + Position);
        this.talon.set(ControlMode.MotionMagic, Position);
        TargetPosition = Position;
    }

    /**used for instrumentation */
    private double getTargetPosition(){
        return TargetPosition;
    }
    /**returns the error in motion magic */
    public double getError(){
        return this.talon.getClosedLoopError();
    }
    /**
     * ReachedPosition
     * <p>Once the closed loop gets to about 200 error in raw encoder count it stops moving;
     * <p>therefore, once the velocity is zero position has been reached
     * @return if velocity is zero
     */
    public boolean reachedPosition(){
        if(this.talon.getSelectedSensorVelocity() == 0){
            return true;
        }else{
            return false;
        }
    }
    /**
     * @return velocity of the talon
     */
    public double getVelocity(){
        return this.talon.getSelectedSensorVelocity(Constants.kPIDLoopIdx);
    }

    /**
     * getPosition()
     * @return Encoder count of talon
     */
    public double getPosition(){
        return this.talon.getSelectedSensorPosition(Constants.kPIDLoopIdx);
    }


    /**Run instrumentation */
    public void instrumentation(){
        
		/* Get current Talon SRX motor output */
		double motorOutput = talon.getMotorOutputPercent();
        /* Prepare line to print */
        sb.append("Talon:");
		sb.append("\tOut%:");
		sb.append(motorOutput);
		sb.append("\tVel:");
		sb.append(talon.getSelectedSensorVelocity(Constants.kPIDLoopIdx));
        sb.append("\terr:");
        sb.append(talon.getClosedLoopError(Constants.kPIDLoopIdx));
        sb.append("\ttrg:");
        sb.append(getTargetPosition());
        Instrumentation.Process(talon, sb);
    }


    /**Treat this as abstract */
    @Override
    protected void initDefaultCommand() {
    }
}
