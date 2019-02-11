package frc.robot.commands.grip;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class IntakeCargo extends Command {

    public IntakeCargo() {
        requires(Robot.superStructure.intake);
        setTimeout(0.5);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.superStructure.intake.openLoop(1);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut() || Robot.superStructure.limitArray[0];
        //return Robot.superStructure.getBallLimitSwitch() || isTimedOut();
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
