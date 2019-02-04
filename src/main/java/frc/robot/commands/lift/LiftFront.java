package frc.robot.commands.lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.PIDCalc;
import frc.robot.Robot;


public class LiftFront extends Command {


    public LiftFront() {
        requires(Robot.liftSystem);
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
        Robot.liftSystem.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
