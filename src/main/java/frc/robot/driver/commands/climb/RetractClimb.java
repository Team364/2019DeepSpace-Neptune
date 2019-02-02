package frc.robot.driver.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class RetractClimb extends Command {

    public RetractClimb() {
        requires(Robot.climbSystem);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.climbSystem.retract();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.climbSystem.noInput();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
