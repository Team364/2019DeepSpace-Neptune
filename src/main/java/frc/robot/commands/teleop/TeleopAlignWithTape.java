package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.VisionSystem;

public class TeleopAlignWithTape extends Command {
    /**
     * 
     */
    public TeleopAlignWithTape() {
        requires(Robot.visionSystem);
        requires(Robot.driveSystem);

    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return false;
       
    }

    @Override
    protected void end() {
    }

}
