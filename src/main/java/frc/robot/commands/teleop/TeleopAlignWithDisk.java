package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.VisionSystem;

public class TeleopAlignWithDisk extends Command {

    /**
     * Command used for teleop control specific to the intake system
     */
    public TeleopAlignWithDisk() {
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
