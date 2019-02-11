package frc.robot.commands.grip;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class OpenLever extends Command {

    public OpenLever() {
        requires(Robot.superStructure.lever);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.superStructure.lever.open();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.superStructure.lever.noInput();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
