package frc.robot.commands.auto.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class ShiftUp extends Command {

    public ShiftUp() {
        requires(Robot.driveSystem);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.driveSystem.shiftHigh();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.driveSystem.noShiftInput();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
