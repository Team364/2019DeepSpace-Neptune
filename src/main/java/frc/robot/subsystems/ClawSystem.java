package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.teleop.defaultcommands.TeleopClawCommand;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.RobotMap;

public class ClawSystem extends Subsystem {
    private TalonSRX rightClaw;
    private TalonSRX leftClaw;

    /**
     * ClawSystem()
     */ 
    public ClawSystem() {
        //initialize talons and or victors here
        rightClaw = new TalonSRX(RobotMap.rightClaw);
        leftClaw= new TalonSRX(RobotMap.leftClaw);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopClawCommand());
    }

}
