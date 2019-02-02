package frc.robot.driver.commands.lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.PIDCalc;
import frc.robot.Robot;


public class KeepPitch extends Command {

    private double EvenPitch = 0;
    private double lowHeight = 0;
    private double highHeight = 5;
    private PIDCalc pidPitch;

    public KeepPitch() {

        requires(Robot.climbSystem);

        pidPitch = new PIDCalc(0, 0, 0, 0, "ClimbPitch");

    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.liftSystem.setLiftPosition(pidPitch.calculateOutput(EvenPitch, Robot.driveSystem.navX.getPitch()));
    }

    @Override
    protected boolean isFinished() {
        if(Robot.liftSystem.getLiftPosition() > lowHeight || Robot.liftSystem.getLiftPosition() < highHeight) {
            return true;
        }
        else {
            return false;
        }

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
