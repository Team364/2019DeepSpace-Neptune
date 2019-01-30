package frc.robot.commands.auto.claw;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class IntakeCargo extends Command {

    public IntakeCargo() {
        requires(Robot.clawSystem);
        setTimeout(0.5);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.clawSystem.runIntakeForward();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.clawSystem.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
