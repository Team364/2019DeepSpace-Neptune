package frc.robot.commands.teleop.buttonsubroutines;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class SetObjectStateCargo extends Command {


    public SetObjectStateCargo() {
        requires(Robot.driveSystem);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.objState = Robot.ObjectStates.CARGO_OBJ; 
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
