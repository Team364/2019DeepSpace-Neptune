package frc.robot.misc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.States;
import frc.robot.misc.subsystems.TalonBase;;

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
