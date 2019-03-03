package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.misc.*;

/**Modes:
 * <p>0: Open
 * <p>1: Close
 * @param mode
 */
public class WaitCommand extends Command {

    public WaitCommand(double t) {
        setTimeout(t);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
         
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
