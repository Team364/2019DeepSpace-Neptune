package frc.robot.commands.grip;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class OuttakeCargo extends Command {

    public OuttakeCargo() {
        requires(Robot.superStructure.intake);
        setTimeout(0.5);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.superStructure.intake.openLoop(-1);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.superStructure.intake.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
