package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class ShiftUp extends Command {

    public ShiftUp() {
        requires(Robot.superStructure.shifter);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.superStructure.shifter.open();
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
