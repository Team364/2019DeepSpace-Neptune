package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.defaultcommands.ArmOpenLoop;
import frc.robot.util.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import frc.robot.util.Instrumentation;
public class ArmSystem extends Subsystem {

    private TalonSRX arm;
    /**Tracks the target angle for instrumentation */
    private double TargetAngle;
    // /**highest absolute count that the arm can rotate to */
    // public double upperBound = 4096;
    // /**lowest absolute count that the arm can rotate to*/
    // public double lowerBound = 0;
    
    /* Used to build string throughout loop */
    StringBuilder sb = new StringBuilder();
    /**
     * ArmSystem()
     */ 
    public ArmSystem() {
        //initialize talons and or victors here
        // arm = new TalonSRX(RobotMap.arm);
        arm = new TalonSRX(10);
        arm.configFactoryDefault();
        arm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,	// Feedback
        Constants.kPIDLoopIdx, 											// PID ID
        Constants.kTimeoutMs);

		/**
		 * Configure Talon SRX Output and Sesnor direction accordingly
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		 */
		arm.setSensorPhase(true);
		arm.setInverted(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		arm.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		arm.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		/* Set the peak and nominal outputs */
		arm.configNominalOutputForward(0, Constants.kTimeoutMs);
		arm.configNominalOutputReverse(0, Constants.kTimeoutMs);
		arm.configPeakOutputForward(0.25, Constants.kTimeoutMs);
		arm.configPeakOutputReverse(-0.25, Constants.kTimeoutMs);

		/* Set Motion Magic gains in slot0 - see documentation */
		arm.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		arm.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
		arm.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
		arm.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
		arm.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);

		/* Set acceleration and vcruise velocity - see documentation */
		arm.configMotionCruiseVelocity(3750, Constants.kTimeoutMs);
		arm.configMotionAcceleration(1500, Constants.kTimeoutMs);

		/* Zero the sensor */
		arm.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ArmOpenLoop());
    }
    public void openLoop(double power){
        //Move arm
        power *= 0.25;
        //Because the arm is supposed to be very powerful
        //0.2 is used to tone it down
        //The number 0.2 is arbitrary
        //feed adjusted power into the talon set method
        arm.set(ControlMode.PercentOutput, power);
   
    }
    public void setArmAngle(double Angle){
        
        System.out.println("The arm is moving to: " + Angle);
        arm.set(ControlMode.MotionMagic, Angle);
        TargetAngle = Angle;
    }

    public void stop(){
        //Set the arm to zero here
        arm.set(ControlMode.PercentOutput, 0);
    }
      	/**
	 * @param units CTRE mag encoder sensor units 
	 * @return degrees rounded to tenths.
	 */
	Double ToDeg(int units) {
		double deg = units * 360.0 / 4096.0;

		/* truncate to 0.1 res */
		deg *= 10;
		deg = (int) deg;
		deg /= 10;

		return deg;
  }
 
    public Double ToRaw(double units){
        double raw = units * 4096.0 / 360.0;
        return raw;
    }
//   public double getAbsolutePosition() {
//     /* get the absolute pulse width position */
//     int pulseWidth = arm.getSensorCollection().getPulseWidthPosition();

//     /**
//      * Mask out the bottom 12 bits to normalize to [0,4095],
//      * or in other words, to stay within [0,360) degrees 
//      */
//     pulseWidth = pulseWidth & 0xFFF;

//     /* Update Quadrature position */
//     arm.getSensorCollection().setQuadraturePosition(pulseWidth, Constants.kTimeoutMs);    
//     return ToDeg(arm.getSensorCollection().getQuadraturePosition());
// }
    private double getTargetAngle(){
    return TargetAngle;
}
    /**
     * ReachedPosition
     * <p>Once the closed loop gets to about 200 error in raw encoder count it stops moving;
     * <p>therefore, once the velocity is zero position has been reached
     * @return if velocity is zero
     */
    public boolean reachedPosition(){
        if(arm.getSelectedSensorVelocity() == 0){
            return true;
        }else{
            return false;
        }

    }
    public void zero(){
        arm.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    }
     /**
     * getLiftPosition()
     * @return Encoder count of lift
     */
    public double getPosition(){
        return arm.getSelectedSensorPosition(Constants.kPIDLoopIdx);
    }
    /**Run instrumentation */
    public void instrumentation(){
        
		/* Get current Talon SRX motor output */
		double motorOutput = arm.getMotorOutputPercent();
        /* Prepare line to print */
        sb.append("Arm:");
		sb.append("\tOut%:");
		sb.append(motorOutput);
		sb.append("\tVel:");
		sb.append(arm.getSelectedSensorVelocity(Constants.kPIDLoopIdx));
        sb.append("\terr:");
        sb.append(arm.getClosedLoopError(Constants.kPIDLoopIdx));
        sb.append("\ttrg:");
        sb.append(getTargetAngle());
        Instrumentation.Process(arm, sb);
    }
}
