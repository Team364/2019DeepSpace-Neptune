package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Robot;
import frc.robot.RobotMap;

public class ClimbSystem extends Subsystem {

    private DoubleSolenoid Pistons;
    private DoubleSolenoid HabWheels;

    private double navXpitch;
    /**
     * ClimbSystem()
     */ 
    public ClimbSystem() {
        //initialize talons and or victors here
        Pistons = new DoubleSolenoid(RobotMap.climbPort1, RobotMap.climbPort2);
        HabWheels = new DoubleSolenoid(RobotMap.climbPort3, RobotMap.climbPort4);
    
        navXpitch = 100;
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
     * closeClaw()
     * <p>Retract Climb Mechanism
     */ 
    public void retract() {
        Pistons.set(DoubleSolenoid.Value.kReverse);
    }

    public boolean ReachedPitch(){
        // checks if the robot has reached pitch(height)
        // make sure to change navXpitch later on
        if(Robot.driveSystem.getGyroAngle() > navXpitch) {
            return true;
        }
        else {
            return false;
        }

    }

    public void EngageWheels(){
        HabWheels.set(DoubleSolenoid.Value.kForward);
    }

    public void DisengageWheels(){
        HabWheels.set(DoubleSolenoid.Value.kReverse);
    }
    /**
     * noInput()
     * Leaves the scoring mechanism where it is
     */ 
    public void noInput() {
        Pistons.set(DoubleSolenoid.Value.kOff);
    }

}
