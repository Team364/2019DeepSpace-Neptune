package frc.robot.commands.grip;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class IntakeCargo extends Command {

    public IntakeCargo() {
        requires(Robot.gripSystem);
        setTimeout(0.5);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.gripSystem.runIntakeForward();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
        //return Robot.gripSystem.getBallLimitSwitch() || isTimedOut();
    }

    @Override
    protected void end() {
        Robot.gripSystem.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
