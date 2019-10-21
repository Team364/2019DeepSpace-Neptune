package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.States;
import frc.robot.commands.SetPiston;

public class SetIntakePos extends Command {

    private Command setClaw;
    private Command setLever;
    private Command elevate;
    private boolean finish = false;

    public SetIntakePos() {
        if (States.objState == States.ObjectStates.CARGO_OBJ) {
            // requires(Neptune.trident.claw);
        } else if (States.objState == States.ObjectStates.HATCH_OBJ) {
            // requires(Neptune.trident.lever);
        }
    }

    @Override
    protected void initialize() {
        finish = false;
        States.actionState = States.ActionStates.SEEK;
    }

    @Override
    protected void execute() {
        if (States.objState == States.ObjectStates.CARGO_OBJ) {
            setClaw = new SetPiston(Neptune.trident.claw, 1);
            setLever = new SetPiston(Neptune.trident.lever, 1);
            setClaw.start();
            setLever.start();
            end();
        } else if (States.objState == States.ObjectStates.HATCH_OBJ) {
            setLever = new SetPiston(Neptune.trident.lever, 0);
            setClaw = new SetPiston(Neptune.trident.claw, 0);
            setLever.start();
            setClaw.start();
            end();
        }
    }

    @Override
    protected boolean isFinished() {
        return finish;
    }

    @Override
    protected void end() {
        if (States.objState == States.ObjectStates.CARGO_OBJ) {
            Neptune.trident.claw.noInput();
        } else if (States.objState == States.ObjectStates.HATCH_OBJ) {
            Neptune.trident.lever.noInput();
        }
        elevate = new ElevateToPosition(0);
        elevate.start();
        finish = true;
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
