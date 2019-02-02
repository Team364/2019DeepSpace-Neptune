package frc.robot.driver.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.DriveSystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveForPower extends Command {

    public double m_power;

    public DriveForPower(double power, double time){
        requires(Robot.driveSystem);
        setTimeout(time);
        m_power = power;
    }

    @Override
    protected void initialize() {
        
    }

    @Override
    protected void execute() {
        Robot.driveSystem.driveForPower(m_power);

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
