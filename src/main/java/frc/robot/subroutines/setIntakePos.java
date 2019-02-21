package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.States;
import frc.robot.misc.subsystems.*;

public class setIntakePos extends Command {
    
    private Command elevate;
    public setIntakePos() {
        requires(Neptune.trident);
        setTimeout(1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        if(States.objState == States.ObjectStates.CARGO_OBJ){
            Neptune.trident.close();
        }else if(States.objState == States.ObjectStates.HATCH_OBJ){
            Neptune.trident.open();
        }
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Neptune.trident.noInput();
        elevate = new ElevateToPosition(0);
        elevate.start();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
