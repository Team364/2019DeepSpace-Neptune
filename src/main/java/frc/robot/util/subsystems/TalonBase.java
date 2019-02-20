package frc.robot.util.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
// import com.ctre.phoenix.motorcontrol.can.VictorSPX;

// import frc.robot.RobotMap;
// import frc.robot.Robot;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.util.subsystems.talonutil.*;
  /**
   * TalonBase subsystem with getters and setters for all relevant funtionality
   * <p>includes closed loop and open loop funtionality
   * @param talon talon that is to be used in the talonBase
   * @param nominalOutputForward lowest speed the trajectory can run at in the reverse direction
   * @param nominalOutputReverse lowest speed the trajectory can run at in the reverse direction
   * @param peakOutputForward highest speed the trajectory can run at in the forward direction
   * @param peakOutputReverse highest speed the trajectory can run at in the forward direction
   * @param cruiseVelocity talon Cruise velocity maximum for motion magic closed loop control
   * <p>Sensor units per 100ms - 4096 sensor units per revolution
   * @param acceleration talon Acceleration for motion magic closed loop control
     * <p>Sensor units per 100ms - 4096 sensor units per revolution
   * @param upperBound highest raw encoder count that the talon can reach
   * @param lowerBound lowest raw encoder count that the talon can reach
     * <p>set to one for no effect
   */
public class TalonBase extends Subsystem {
    /**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
    public static final int SlotIdx = 0;
    /**
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public static final int PIDLoopIdx = 0;
	/**
	 * set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
	public static final int TimeoutMs = 30;
	/**
	 * Gains used in Motion Magic, to be adjusted accordingly
     * Gains(kp, ki, kd, kf, izone, peak output);
     */
    public static final TalonGains Gains = new TalonGains(0.2, 0.0, 0.0, 0.2, 0, 1.0);
    /**highest raw encoder count that the talon can reach */
    public double upperBound = 30000;
    /**lowest raw encoder count that the talon can reach */
    public double lowerBound = 0;
    /**Power that the talon is set to in Open Loop */
    public double openLoopPower = 0;
    /**Open loop out of bounds. */
    private boolean outOfBounds = false;

    /**Talon */
    private TalonSRX talon;
    /* Used to build string throughout loop */
    StringBuilder sb = new StringBuilder();
    /**Tracks the target position for instrumentation */
    private double TargetPosition;
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
    /**Name of Talon */
    private String name = "No Name";
    /**Tracks what loop State the talon is in */
    public enum LoopStates{
        OPEN_LOOP,
        CLOSED_LOOP
    }
    public LoopStates loopState = LoopStates.OPEN_LOOP;
    /**Talon with Closed Loop functionality and Encoder Bounds */
    public TalonBase(
                    TalonSRX talon, 
                    boolean reverse,
                    boolean reverseEncoder,
                    double nominalOutputForward, 
                    double nominalOutputReverse,
                    double peakOutputForward,
                    double peakOutputReverse,
                    int cruiseVelocity,
                    int acceleration,
                    double lowerBound,
                    double upperBound,
                    double P,
                    double I,
                    double D,
                    double F,
                    String name
                    ) {
        this.talon = talon;
        this.nominalOutputForward = nominalOutputForward;
        this.nominalOutputReverse = nominalOutputReverse;
        this.peakOutputForward = peakOutputForward;
        this.peakOutputReverse = peakOutputReverse;
        this.cruiseVelocity = cruiseVelocity;
        this.acceleration = acceleration;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.name = name;
        /*Sets the name of the subsystem */
        this.setName(this.name, this.name);
        /* Factory default hardware to prevent unexpected behavior */
        talon.configFactoryDefault();
        //All followers will do the same

		/* Configure Sensor Source for Pirmary PID */
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
											PIDLoopIdx, 
											TimeoutMs);

		/**
		 * Configure Talon SRX Output and Sesnor direction accordingly
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		 */
		talon.setSensorPhase(reverse);
		talon.setInverted(reverseEncoder);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TimeoutMs);
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, TimeoutMs);

		/* Set the peak and nominal outputs */
		talon.configNominalOutputForward(this.nominalOutputForward, TimeoutMs);
        talon.configNominalOutputReverse(this.nominalOutputReverse, TimeoutMs);
		talon.configPeakOutputForward(this.peakOutputForward, TimeoutMs);
		talon.configPeakOutputReverse(this.peakOutputReverse, TimeoutMs);

		/* Set Motion Magic gains in slot0 - see documentation */
		talon.selectProfileSlot(SlotIdx, PIDLoopIdx);
		talon.config_kF(SlotIdx, F, TimeoutMs);
		talon.config_kP(SlotIdx, P, TimeoutMs);
		talon.config_kI(SlotIdx, I, TimeoutMs);
		talon.config_kD(SlotIdx, D, TimeoutMs);

		/* Set acceleration and vcruise velocity - see documentation */
		talon.configMotionCruiseVelocity(this.cruiseVelocity, TimeoutMs);
		talon.configMotionAcceleration(this.acceleration, TimeoutMs);

		/* Zero the sensor */
		talon.setSelectedSensorPosition(0);
        
    }
    /**Talon with Closed Loop Functionality without Encoder Bounds */
    public TalonBase(
                    TalonSRX talon, 
                    boolean reverse,
                    boolean reverseEncoder,
                    double nominalOutputForward, 
                    double nominalOutputReverse,
                    double peakOutputForward,
                    double peakOutputReverse,
                    int cruiseVelocity,
                    int acceleration,
                    String name
                    ) {
        this.talon = talon;
        this.nominalOutputForward = nominalOutputForward;
        this.nominalOutputReverse = nominalOutputReverse;
        this.peakOutputForward = peakOutputForward;
        this.peakOutputReverse = peakOutputReverse;
        this.cruiseVelocity = cruiseVelocity;
        this.acceleration = acceleration;
        this.name = name;
        /*Sets the name of the subsystem */
        this.setName(this.name, this.name);
        /* Factory default hardware to prevent unexpected behavior */
        talon.configFactoryDefault();
        //All followers will do the same

		/* Configure Sensor Source for Pirmary PID */
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
											PIDLoopIdx, 
											TimeoutMs);

		/**
		 * Configure Talon SRX Output and Sesnor direction accordingly
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		 */
		talon.setSensorPhase(reverse);
		talon.setInverted(reverseEncoder);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TimeoutMs);
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, TimeoutMs);

		/* Set the peak and nominal outputs */
		talon.configNominalOutputForward(this.nominalOutputForward, TimeoutMs);
        talon.configNominalOutputReverse(this.nominalOutputReverse, TimeoutMs);
		talon.configPeakOutputForward(this.peakOutputForward, TimeoutMs);
		talon.configPeakOutputReverse(this.peakOutputReverse, TimeoutMs);

		/* Set Motion Magic gains in slot0 - see documentation */
		talon.selectProfileSlot(SlotIdx, PIDLoopIdx);
		talon.config_kF(SlotIdx, Gains.kF, TimeoutMs);
		talon.config_kP(SlotIdx, Gains.kP, TimeoutMs);
		talon.config_kI(SlotIdx, Gains.kI, TimeoutMs);
		talon.config_kD(SlotIdx, Gains.kD, TimeoutMs);

		/* Set acceleration and vcruise velocity - see documentation */
		talon.configMotionCruiseVelocity(this.cruiseVelocity, TimeoutMs);
		talon.configMotionAcceleration(this.acceleration, TimeoutMs);

		/* Zero the sensor */
		talon.setSelectedSensorPosition(0, PIDLoopIdx, TimeoutMs);
        
    }
    /**Talon without encoder */
    public TalonBase(
                    TalonSRX talon, 
                    String name
                    ) {
        this.talon = talon;
        this.name = name;
        /*Sets the name of the subsystem */
        this.setName(this.name, this.name);
        /* Factory default hardware to prevent unexpected behavior */
        talon.configFactoryDefault();
        //All followers will do the same

		/**
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 */
		talon.setInverted(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TimeoutMs);
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, TimeoutMs);

    }
    /**Treat this as abstract */
    @Override
    protected void initDefaultCommand() {
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
    /**Sets the Pterm of the PID */
    private void setPterm(double Pterm){
        talon.config_kP(SlotIdx, Pterm);
    }
    /**Sets the Iterm of the PID */
    private void setIterm(double Iterm){
        talon.config_kI(SlotIdx, Iterm);
    }
    /**Sets the Dterm of the PID */
    private void setDterm(double Dterm){
        talon.config_kD(SlotIdx, Dterm);
    }
    /**Sets the Dterm of the PID */
    private void setFterm(double Fterm){
        talon.config_kF(SlotIdx, Fterm);
    }
    public void setPID(double Pterm, double Iterm, double Dterm, double Fterm){
        setPterm(Pterm);
        setIterm(Iterm);
        setDterm(Dterm);
        setFterm(Fterm);
    }
    /**Maximum encoder counts that open loop may reach */
    public void setUpperBound(double counts){
        upperBound = counts;
    }
    /**Minimum encoder counts that open loop may reach */
    public void setLowerBound(double counts){
        lowerBound = counts;
    }
    /**Open loop is to run in the default command */
    public void openLoop(double power){
        loopState = LoopStates.OPEN_LOOP;
        /*Deadband of 10% */
        if(Math.abs(power) < 0.1){
            power = 0;
        }
            this.talon.set(ControlMode.PercentOutput, power);
            openLoopPower = power;
    }
    /**
     * Sets the talon Motor output to 0
     */
    public void stop(){
        openLoop(0);
    }
    /**Sets the outsideBounds Boolean to true or false */
    public void isOutsideBounds(boolean value){
        outOfBounds = value;
    }
    /**Gets the outsideBounds Boolean */
    public boolean getOutsideBounds(){
        return outOfBounds;
    }
    /**Sets the encoder value to zero */
    public void zero(){
        talon.setSelectedSensorPosition(0, PIDLoopIdx, TimeoutMs);
   }
    /**
     * MoveToPosition Closed Loop
     */
    public void MoveToPosition(double Position){
        loopState = LoopStates.CLOSED_LOOP;
        System.out.println("The " + this.name +" is moving to: " + Position);
        this.talon.set(ControlMode.MotionMagic, Position);
        TargetPosition = Position;
    }
    /**Velocity Closed Loop
     * @param RPM rotations per minute
     */
    public void RetainVelocity(double RPM){
        double velocity = RPM / 10.0 * 4096.0 / 60.0;
        this.talon.set(ControlMode.Velocity, velocity);
    }

    /**used for instrumentation */
    private double getTargetPosition(){
        return TargetPosition;
    }
    /**returns the error in motion magic */
    public double getError(){
        return this.talon.getClosedLoopError();
    }
    /**returns raw motor output */
    public double getRawOutput(){
        return this.talon.getMotorOutputPercent();
    }
    /**
     * ReachedPosition
     * <p>Once the closed loop gets to about 200 error in raw encoder count it stops moving;
     * <p>therefore, once the velocity is zero position has been reached
     * @return if velocity is zero
     */
    public boolean reachedPosition(){
        if(this.talon.getSelectedSensorVelocity() == 0){
            loopState = LoopStates.OPEN_LOOP;
            return true;
        }else{
            return false;
        }
    }
    /**
     * @return velocity of the talon
     */
    public double getVelocity(){
        return this.talon.getSelectedSensorVelocity(PIDLoopIdx);
    }
    /**@return RPM */
    public double getRPM(){
        return getVelocity() * 10.0 / 4096.0 * 60.0;
    }

    /**
     * getPosition()
     * @return Encoder count of talon
     */
    public double getPosition(){
        return this.talon.getSelectedSensorPosition(PIDLoopIdx);
    }
    /**Returns the name of the talon */
    public String getTalonName(){
        return this.name;
    }
    public boolean noCommand(){
        if(this.getCurrentCommandName() == ""){
          return true;
        }else{
          return false;
        }
      }

    /**Run instrumentation */
    public void instrumentation(){
        Instrumentation.Process(talon);
    }
    public void postSmartDashVars(){
        String name = this.name;
        String vel = name + " Velocity: ";
        String Tpos = name + "Target Position: ";
        String err = name + " Error: ";
        String cCom = name + " Current Command: ";
        String out = name + " Raw Output: ";
        String pos = name + " Position: ";
        String nCom = name + " No Command?: ";
        String oBound = name + " Out of Bounds?: ";
        SmartDashboard.putNumber(Tpos, getTargetPosition());
        SmartDashboard.putNumber(vel, getVelocity());
        SmartDashboard.putNumber(err, getError());
        SmartDashboard.putString(cCom, getCurrentCommandName());
        SmartDashboard.putNumber(out, getRawOutput());
        SmartDashboard.putNumber(pos, getPosition());
        SmartDashboard.putBoolean(nCom, noCommand());
        SmartDashboard.putBoolean(oBound, getOutsideBounds());
    }



}
