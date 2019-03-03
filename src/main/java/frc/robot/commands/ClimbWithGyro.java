package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

/**Modes:
 * <p>0: Open
 * <p>1: Close
 * @param mode
 */
public class ClimbWithGyro extends Command {

    double angle;

    public ClimbWithGyro(double angle, double timeout) {
        setTimeout(timeout);
        this.angle = angle;
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
