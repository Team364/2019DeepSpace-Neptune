package frc.robot.commands.lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class LiftFront extends Command {


    public LiftFront() {
        requires(Robot.superStructure.lift);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        Robot.superStructure.lift.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
