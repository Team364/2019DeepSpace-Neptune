package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.misc.*;

/**Modes:
 * <p>0: Open
 * <p>1: Close
 * @param mode
 */
public class DriveClimberForwards extends Command {

    double angle;

    public DriveClimberForwards() {
        requires(Neptune.driveTrain);
        setTimeout(2);
    }

    @Override
    protected void initialize() {
        Neptune.climber.keepCurrentPosition();
    }

    @Override
    protected void execute() {
        Neptune.driveTrain.openLoop(0.4, 0.4);
        Neptune.climber.driveWheelsToWin();    
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Neptune.driveTrain.stop();
        Neptune.climber.stop();
    }
}