package frc.robot.driver.subroutines.held;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.driver.subsystems.VisionSystem;

public class Align extends Command {

    /**
     *No Code yet
     */
    public Align() {
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
