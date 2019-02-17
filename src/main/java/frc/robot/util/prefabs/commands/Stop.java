package frc.robot.util.prefabs.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.util.States;
import frc.robot.util.prefabs.subsystems.TalonBase;;

public class Stop extends Command {

    private TalonBase talon;

    public Stop(TalonBase talon) {
        this.talon = talon;
        setTimeout(0.2);
        requires(talon);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        talon.stop();
        States.loopState = States.LoopStates.OPEN_LOOP;
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        talon.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
