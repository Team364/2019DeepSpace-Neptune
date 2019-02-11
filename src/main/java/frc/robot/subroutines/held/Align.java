package frc.robot.subroutines.held;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Align extends Command {

    /**
     *No Code yet - put vision stuff here 
     */
    public Align() {
        requires(Robot.vision);
        requires(Robot.superStructure.rightDrive);
        requires(Robot.superStructure.leftDrive);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.superStructure.rightDrive.stop();
        Robot.superStructure.leftDrive.stop();
    }

}
