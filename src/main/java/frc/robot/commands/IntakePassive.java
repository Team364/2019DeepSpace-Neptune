package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.States;
import frc.robot.util.prefabs.subsystems.*;
public class IntakePassive extends Command {

    private Command cl;
    private Command le;

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
            cl = new SetPiston(Robot.superStructure.claw, 1);
            le = new SetPiston(Robot.superStructure.lever, 1);
            cl.start();
            le.start();
        }else if(States.objState == States.ObjectStates.CARGO_OBJ){
            cl = new SetPiston(Robot.superStructure.claw, 0);
            le = new SetPiston(Robot.superStructure.lever, 0);
            cl.start();
            le.start();
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
