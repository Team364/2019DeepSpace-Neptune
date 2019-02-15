package frc.robot.util.prefabs.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.States;
import frc.robot.util.prefabs.subsystems.TalonBase;;

public class ClosedLoop extends Command {

    private TalonBase talon;
    private double Position;
    private int loops;
    /**
     * Heights
     * <p>1: low on rocket, scoring hatches on rocket level 1 and Cargo Ship
     * <p>2: middle on rocket
     * <p>3: high on rocket
     * <p>4: cargo height for scoring on Cargo Ship
     * @param Position
     * WORKS
     */
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
        States.loopState = States.LoopStates.CLOSED_LOOP;
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
