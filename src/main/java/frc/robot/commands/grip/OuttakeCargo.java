package frc.robot.commands.grip;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class OuttakeCargo extends Command {

    public OuttakeCargo() {
        requires(Robot.gripSystem);
        setTimeout(0.5);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.gripSystem.runIntakeBackward();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
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
