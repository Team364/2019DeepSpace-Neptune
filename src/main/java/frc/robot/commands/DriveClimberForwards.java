package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

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
        Neptune.driveTrain.openLoop(0.5, 0.5);
        Neptune.climber.driveLevitator(0.6);    
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