package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

public class ClimbWithGyro extends Command {

    double angle;

    public ClimbWithGyro(double angle, double timeout) {
        setTimeout(timeout);
        this.angle = angle;
        requires(Neptune.climber);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() { 
        Neptune.climber.levitateWithGyro(angle);  
        Neptune.climber.driveWheelsToWin();   
        System.out.println("Climb with gyro at " + angle + " is executing");
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Neptune.climber.keepCurrentPosition();
        System.out.println("Climb with Gyro Complete");
    }

}
