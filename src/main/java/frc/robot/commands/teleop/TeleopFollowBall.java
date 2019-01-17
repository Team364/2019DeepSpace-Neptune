package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

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
        //Robot.visionSystem.setupSearchForBall();
    }

    @Override
    protected void execute() {
        if (Robot.visionSystem.visionTargetSeen) {

            // I see a target! 
            double centerX = Robot.visionSystem.centerX;
            double targetArea = Robot.visionSystem.targetArea;

            // Interpret location(s) of target and drive accordingly
            double pidOutputXvalue = Robot.driveSystem.pidXvalue.calculateOutput(120, centerX);
            double pidOutputAvalue = Robot.driveSystem.pidAvalue.calculateOutput(800, targetArea);
            double visionLeft = pidOutputXvalue + pidOutputAvalue;
            double visionRight = -pidOutputXvalue + pidOutputAvalue;

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
