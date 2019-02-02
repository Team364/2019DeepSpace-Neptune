package frc.robot.driver.commands.lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class KeepPitch extends Command {

    private double lowPitch;
    private double highPitch;

    public KeepPitch() {
        lowPitch = -5;
        highPitch = 5;
        requires(Robot.climbSystem);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        if(Robot.driveSystem.navX.getPitch() < lowPitch) {

        }
        else if(Robot.driveSystem.navX.getPitch() > highPitch) {

        }
        else {
            
        }
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.liftSystem.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
