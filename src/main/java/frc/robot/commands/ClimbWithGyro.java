package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.misc.*;

/**Modes:
 * <p>0: Open
 * <p>1: Close
 * @param mode
 */
public class ClimbWithGyro extends Command {

    double angle;

    public ClimbWithGyro(double a) {
        setTimeout(3);
        angle = a;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() { 
        Neptune.climber.levitateWithGyro(angle);    
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Neptune.climber.keepCurrentPosition();
    }
}
