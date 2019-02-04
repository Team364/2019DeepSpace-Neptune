package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.defaultcommands.manualArm;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.RobotMap;

public class ArmSystem extends Subsystem {

    //private TalonSRX arm
    /**
     * ArmSystem()
     */ 
    public ArmSystem() {
        //initialize talons and or victors here
        // arm = new TalonSRX(RobotMap.arm);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new manualArm());
    }
    public void moveArm(double power){
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

}
