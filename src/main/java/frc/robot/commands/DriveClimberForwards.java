package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

public class DriveClimberForwards extends Command {

    double angle;

    public DriveClimberForwards() {
        requires(Neptune.driveTrain);
        setTimeout(1.4);
    }

    @Override
    protected void initialize() {
        Neptune.climber.keepCurrentPosition();
    }

    @Override
    protected void execute() {
        Neptune.driveTrain.openLoop(0.4, 0.4);
        Neptune.climber.driveWheelsToWin();  
        System.out.println("Drive Climb Forward is executing");
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Neptune.driveTrain.stop();
        Neptune.climber.stop();
        System.out.println("Drive Climb  Forward has ended");
    }
}