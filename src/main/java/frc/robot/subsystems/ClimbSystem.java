package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Robot;
import frc.robot.RobotMap;

public class ClimbSystem extends Subsystem {

    private DoubleSolenoid Pistons;
    private DoubleSolenoid Wheels;
    /**
     * ClimbSystem()
     */ 
    public ClimbSystem() {
        //initialize talons and or victors here
        Pistons = new DoubleSolenoid(RobotMap.climbPort1, RobotMap.climbPort2);
        Wheels = new DoubleSolenoid(RobotMap.climbPort3, RobotMap.climbPort4);
    }

    @Override
    protected void initDefaultCommand() {
       //No. Just no. This should not be accessable by default.
    }
    //TODO: Make sure these are set correctly. Open should open. Close should close
    //TODO: Find better name for this. Ask mechanincal what they're calling this device later on
    /**
     * release()
     * <p>Extend Climb Mechanism
     */ 
    public void release() {
        Pistons.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * retract()
     * <p>Retract Climb Mechanism
     */ 
    public void retract() {
        Pistons.set(DoubleSolenoid.Value.kReverse);
    }
    /**
     * noInput()
     * <p>Leaves the scoring mechanism where it is
     */ 
    public void noInput() {
        Pistons.set(DoubleSolenoid.Value.kOff);
    }
    /**
     * releaseWheels()
     * <p>releases contact wheels for climbing
     */
    public void releaseWheels(){
        Wheels.set(DoubleSolenoid.Value.kForward);
    }
    /**
     * retractWheels()
     * <p>retracts contact wheels after climbing
     */
    public void retractWheels(){
        Wheels.set(DoubleSolenoid.Value.kReverse);
    }
    /**
     * noInput()
     * <p>Leaves the contact mechanism where it is
     */ 
    public void noInputWheels() {
        Wheels.set(DoubleSolenoid.Value.kOff);
    }

}
