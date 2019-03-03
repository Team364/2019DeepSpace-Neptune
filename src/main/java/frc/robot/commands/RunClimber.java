package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.misc.*;

/**Modes:
 * <p>0: Open
 * <p>1: Close
 * @param mode
 */
public class RunClimber extends Command {

    public RunClimber() {
        setTimeout(0.5);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {    
        Neptune.climber.driveLevitator(0.25);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Neptune.climber.driveLevitator(0);
    }
}
