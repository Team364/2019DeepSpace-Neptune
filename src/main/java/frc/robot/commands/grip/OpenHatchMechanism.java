package frc.robot.commands.grip;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class OpenHatchMechanism extends Command {

    public OpenHatchMechanism() {
        requires(Robot.gripSystem);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.gripSystem.openLevers();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.gripSystem.noLeverInput();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
