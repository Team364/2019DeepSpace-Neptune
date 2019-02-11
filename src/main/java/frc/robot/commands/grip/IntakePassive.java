package frc.robot.commands.grip;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.States;

public class IntakePassive extends Command {

    public IntakePassive() {
        requires(Robot.superStructure.claw);
        requires(Robot.superStructure.lever);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        if(States.objState == States.ObjectStates.HATCH_OBJ){
            Robot.superStructure.claw.close();
            Robot.superStructure.lever.close();
        }else if(States.objState == States.ObjectStates.CARGO_OBJ){
            Robot.superStructure.claw.open();
            Robot.superStructure.lever.open();
        }
        
        
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.superStructure.claw.noInput();
        Robot.superStructure.lever.noInput();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
