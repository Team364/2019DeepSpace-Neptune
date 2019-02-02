package frc.robot.operator.commands.claw;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class CloseClaw extends Command {

    public CloseClaw() {
        requires(Robot.gripSystem);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.gripSystem.closeClaw();;
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.gripSystem.noClawInput();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
