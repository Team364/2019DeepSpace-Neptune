package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class RetractWheels extends Command {

    public RetractWheels() {
        requires(Robot.superStructure.wheels);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.superStructure.wheels.close();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.superStructure.wheels.noInput();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
