package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.teleop.defaultcommands.TeleopClawCommand;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.RobotMap;

public class ClawSystem extends Subsystem {

    /**
     * ClawSystem()
     */ 
    public ClawSystem() {
        //initialize talons and or victors here
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopClawCommand());
    }

}
