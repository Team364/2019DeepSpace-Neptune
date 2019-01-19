package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.VisionSystem;

public class TeleopCenterOnBall extends Command {

    public static double centerX = 0.0;
    public static double targetArea = 0.0;
    public static boolean visionTargetSeen = false;  
    private static boolean reachedX;
    double visionLeft;
    double visionRight;

    /**
     * Command used for teleop control specific to the intake system
     */
    public TeleopCenterOnBall() {
        requires(Robot.visionSystem);
        requires(Robot.driveSystem);
    }

    @Override
    protected void initialize() {
        reachedX = false;
        Robot.visionSystem.DesiredX = VisionSystem.ballDesiredX;
        Robot.visionSystem.setupSearchForBall();
        Robot.driveSystem.pidXvalue.resetPID();
        Robot.driveSystem.pidXvalue.setPIDParameters(0.003, 0.00001, 0.0, 0.0);
    }

    @Override
    protected void execute() {
      
        if (Robot.visionSystem.visionTargetSeen) {

            // I see a target! 
            // Interpret location(s) of target and drive accordingly
            if(!reachedX){
            Robot.driveSystem.turnToVisionTarget();
            }
            
            if(Math.abs(Robot.driveSystem.pidXvalue.getError()) <= 6){
                reachedX = true;
                System.out.println("Ball is centered");
                Robot.driveSystem.tankDrive(0, 0);
            }
           
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
