package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class TestPGyro extends Command {

    public TestPGyro() {
        requires(Robot.driveSystem);
        setTimeout(3);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
    //    Robot.driveSystem.getPigeonGyro(); 
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
