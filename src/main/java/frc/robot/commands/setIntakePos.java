package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.States;
import frc.robot.util.prefabs.commands.SetPiston;


public class setIntakePos extends Command {
    
    private double power;
    private boolean intaking;
    private Command setClaw;
    private Command setLever;
    public setIntakePos() {
        if(States.objState == States.ObjectStates.CARGO_OBJ){
            requires(Robot.superStructure.claw);
        }else if(States.objState == States.ObjectStates.HATCH_OBJ){
            requires(Robot.superStructure.lever);
        }
        setTimeout(1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        if(States.objState == States.ObjectStates.CARGO_OBJ){
            setClaw = new SetPiston(Robot.superStructure.claw, 0);
            setLever = new SetPiston(Robot.superStructure.lever, 1);
            setClaw.start();
            setLever.start();
        }else if(States.objState == States.ObjectStates.HATCH_OBJ){
            setLever = new SetPiston(Robot.superStructure.lever, 0);
            setClaw = new SetPiston(Robot.superStructure.claw, 1);
            setLever.start();
            setClaw.start();
        }
    }

    @Override
    protected boolean isFinished() {
            return isTimedOut();
    }

    @Override
    protected void end() {
        if(States.objState == States.ObjectStates.CARGO_OBJ){
           Robot.superStructure.claw.noInput();
        }else if(States.objState == States.ObjectStates.HATCH_OBJ){
           Robot.superStructure.lever.noInput();
        }
        Command elevate = new ElevateToPosition(0);
        elevate.start();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
