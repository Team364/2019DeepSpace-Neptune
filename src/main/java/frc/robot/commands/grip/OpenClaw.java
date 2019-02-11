package frc.robot.commands.grip;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class OpenClaw extends Command {

    public OpenClaw() {
        requires(Robot.superStructure.claw);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.superStructure.claw.open();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.superStructure.claw.noInput();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
