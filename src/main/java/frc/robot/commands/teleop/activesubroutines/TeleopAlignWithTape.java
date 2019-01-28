package frc.robot.commands.teleop.activesubroutines;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.VisionSystem;

public class TeleopAlignWithTape extends Command {
    /**
     * No code yet
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
