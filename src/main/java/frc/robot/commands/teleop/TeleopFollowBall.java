package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.VisionSystem;

public class TeleopFollowBall extends Command {

    public static double centerX = 0.0;
    public static double targetArea = 0.0;
    public static boolean visionTargetSeen = false;  

    /**
     * Command used for teleop control specific to the intake system
     */
    public TeleopFollowBall() {
        requires(Robot.visionSystem);
        requires(Robot.driveSystem);
    }

    @Override
    protected void initialize() {
        Robot.visionSystem.setupSearchForBall();
        Robot.driveSystem.pidXvalue.setPIDParameters(0.001, 0.0, 0.0, 0.0);
    }

    @Override
    protected void execute() {
        if (Robot.visionSystem.visionTargetSeen) {

            // I see a target! 
            double centerX = Robot.visionSystem.centerX;
            double targetArea = Robot.visionSystem.targetArea;

            // Interpret location(s) of target and drive accordingly
            double pidOutputXvalue = Robot.driveSystem.pidXvalue.calculateOutput(VisionSystem.ballDesiredX, centerX);
            double visionLeft = pidOutputXvalue;
            double visionRight = -pidOutputXvalue;

            Robot.driveSystem.tankDrive(visionLeft, visionRight);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

}
