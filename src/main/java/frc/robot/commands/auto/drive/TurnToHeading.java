package frc.robot.commands.auto.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class TurnToHeading extends Command {

    private double wantedHeading;

    public TurnToHeading(double heading) {
        requires(Robot.driveSystem);
        wantedHeading = heading;
        setTimeout(3);
    }

    @Override
    protected void initialize() {
        if(wantedHeading >= 170){ 
            Robot.driveSystem.pidNavX.setPIDParameters(0.0075, 0, 0, 0);
        }else if(wantedHeading >= 60){  
            Robot.driveSystem.pidNavX.setPIDParameters(0.0095, 0, 0, 0);
        }else{      
            Robot.driveSystem.pidNavX.setPIDParameters(0.015, 0, 0, 0);
        }
      
        Robot.driveSystem.stop();
        Robot.driveSystem.resetHeading();
        Robot.driveSystem.pidNavX.resetPID();
    }

    @Override
    protected void execute() {
        Robot.driveSystem.turnToHeading(wantedHeading);
        SmartDashboard.putNumber("Heading", Robot.driveSystem.getGyroAngle());
    }

    @Override
    protected boolean isFinished() {
        return Robot.driveSystem.reachedHeading(wantedHeading) || isTimedOut();
    }

    @Override
    protected void end() {
        Robot.driveSystem.stop();
        Robot.driveSystem.resetHeading();
        System.out.println("Reached target heading." + Robot.driveSystem.getGyroAngle());
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
