package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.DriveSystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveStraightForPower extends Command {

    public double m_power;

    public DriveStraightForPower(double power, double time){
        requires(Robot.driveSystem);
        setTimeout(time);
        m_power = power;
    }

    @Override
    protected void initialize() {
        // Robot.driveSystem.pidNavX.setPIDParameters(0.00005, 0, 0, 0);
    }

    @Override
    protected void execute() {
        Robot.driveSystem.keepHeading(-m_power);
        // SmartDashboard.putNumber("pidOutputGyroStraight", Robot.driveSystem.pidOutputNavX);

    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.driveSystem.noShiftInput();
        Robot.driveSystem.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
