package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.States;
import frc.robot.commands.SetPiston;


public class setIntakePos extends Command {
    
    private Command setClaw;
    private Command setLever;
    private Command elevate;
    public setIntakePos() {
        if(States.objState == States.ObjectStates.CARGO_OBJ){
            requires(Robot.trident.claw);
        }else if(States.objState == States.ObjectStates.HATCH_OBJ){
            requires(Robot.trident.lever);
        }
        setTimeout(1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        if(States.objState == States.ObjectStates.CARGO_OBJ){
            setClaw = new SetPiston(Robot.trident.claw, 1);
            setLever = new SetPiston(Robot.trident.lever, 1);
            setClaw.start();
            setLever.start();
        }else if(States.objState == States.ObjectStates.HATCH_OBJ){
            setLever = new SetPiston(Robot.trident.lever, 0);
            setClaw = new SetPiston(Robot.trident.claw, 0);
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
           Robot.trident.claw.noInput();
        }else if(States.objState == States.ObjectStates.HATCH_OBJ){
           Robot.trident.lever.noInput();
        }
        elevate = new ElevateToPosition(0);
        elevate.start();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
