package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.defaultcommands.ArmOpenLoop;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class ArmSystem extends Subsystem {

    //private TalonSRX arm
    /**
     * ArmSystem()
     */ 
    public ArmSystem() {
        //initialize talons and or victors here
        // arm = new TalonSRX(RobotMap.arm);
        // arm.configFactoryDefault();
        // arm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,	// Feedback
        // 0, 											// PID ID
        // kTimeoutMs);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ArmOpenLoop());
    }
    public void ArmOpenLoop(double power){
        //Move arm
        double adjustedPower = power*0.2;
        //Because the arm is supposed to be very powerful
        //0.2 is used to tone it down
        //The number 0.2 is arbitrary
        //feed adjusted power into the talon set method
        if(power >= 0.5){
            System.out.println("Arm is moving");
        }
   
    }
    public void ArmClosedLoop(){
     
    }

    public void stop(){
        //Set the arm to zero here
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
  public double getAbsolutePosition() {
    // /* get the absolute pulse width position */
    // int pulseWidth = arm.getSensorCollection().getPulseWidthPosition();

    // /**
    //  * Mask out the bottom 12 bits to normalize to [0,4095],
    //  * or in other words, to stay within [0,360) degrees 
    //  */
    // pulseWidth = pulseWidth & 0xFFF;

    // /* Update Quadrature position */
    // arm.getSensorCollection().setQuadraturePosition(pulseWidth, kTimeoutMs);    
    // return ToDeg(arm.getSensorCollection().getQuadraturePosition());
    return 0;
}
}
