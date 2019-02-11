package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class ShiftDown extends Command {

    public ShiftDown() {
        requires(Robot.superStructure.shifter);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.superStructure.shifter.close();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.superStructure.shifter.noInput();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
