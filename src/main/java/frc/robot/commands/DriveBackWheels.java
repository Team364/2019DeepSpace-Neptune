package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

public class DriveBackWheels extends Command {

    double angle;

    public DriveBackWheels() {
        requires(Neptune.driveTrain);
        setTimeout(1.4);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Neptune.climber.driveWheelsSlow();  
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
    }
}