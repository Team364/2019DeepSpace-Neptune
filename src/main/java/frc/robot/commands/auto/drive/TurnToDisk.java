package frc.robot.commands.auto.drive;

import edu.wpi.first.vision.VisionPipeline;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.VisionSystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class TurnToDisk extends Command {

    private double centerX;

    public TurnToDisk() {
        requires(Robot.driveSystem);
        centerX = Robot.visionSystem.centerX;
        setTimeout(1.5);
    }

    @Override
    protected void initialize() {
        Robot.visionSystem.DesiredX = VisionSystem.diskDesiredX;
        Robot.driveSystem.pidXvalue.setPIDParameters(0.0095, 0, 0, 0);
        Robot.driveSystem.stop();
    }

    @Override
    protected void execute(){
        Robot.driveSystem.turnToVisionTarget();
        SmartDashboard.putNumber("Center X Disk ", Robot.visionSystem.centerX);
    }

    @Override
    protected boolean isFinished() {
        return Robot.visionSystem.alignedWithDisk() || isTimedOut();
    }

    @Override
    protected void end() {
        Robot.driveSystem.stop();
        Robot.driveSystem.resetHeading();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
