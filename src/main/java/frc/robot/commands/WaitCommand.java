package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

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
    protected boolean isFinished() {
        return isTimedOut();
    }
    
}
