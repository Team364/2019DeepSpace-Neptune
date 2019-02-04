package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class ReleaseWheels extends Command {

    public ReleaseWheels() {
        requires(Robot.climbSystem);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.climbSystem.releaseWheels();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.climbSystem.noInputWheels();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
