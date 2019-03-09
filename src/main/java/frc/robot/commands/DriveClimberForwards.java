package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.RobotMap;

public class DriveClimberForwards extends Command {

    double angle;

    public DriveClimberForwards() {
        requires(Neptune.driveTrain);
        setTimeout(1.6);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Neptune.driveTrain.openLoop(0.4, 0.4);
        Neptune.climber.driveWheelsToWin();  
        Neptune.climber.levitateToPos(RobotMap.climbLevitate);
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
        Neptune.climber.levitateToPos(100);
        System.out.println("Drive Climb  Forward has ended");
    }
}