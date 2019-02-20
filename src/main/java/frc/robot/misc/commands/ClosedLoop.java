package frc.robot.misc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.misc.subsystems.TalonBase;;

public class ClosedLoop extends Command {

    private TalonBase talon;
    private double Position;
    private int loops;

    public ClosedLoop(TalonBase talon, int Position) {
        this.talon = talon;
        this.Position = Position;  
        setTimeout(0.2);
        requires(talon);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        ++loops;
        talon.MoveToPosition(Position);
    }

    @Override
    protected boolean isFinished() {
        return talon.reachedPosition()&& (loops > 20);
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
