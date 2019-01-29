package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.teleop.defaultcommands.TeleopLiftCommand;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.RobotMap;

public class LiftSystem extends Subsystem {

    /**
     * LiftSystem()
     */ 
    public LiftSystem() {
        //initialize talons and or victors here
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopLiftCommand());
    }

}
