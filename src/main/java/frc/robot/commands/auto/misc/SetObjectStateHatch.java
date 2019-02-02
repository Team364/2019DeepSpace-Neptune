package frc.robot.commands.auto.misc;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class SetObjectStateHatch extends Command {


    public SetObjectStateHatch() {
        requires(Robot.driveSystem);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.objState = Robot.ObjectStates.HATCH_OBJ; 
    }

    @Override
    protected boolean isFinished() {
        return  isTimedOut();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
