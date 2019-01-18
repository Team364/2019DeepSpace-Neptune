package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.VisionSystem;

public class TeleopAlignWithTape extends Command {
    //public static VisionPipeline vision;

    //public static VisionThread visionThread;
    public static double centerX = 0.0;
    public static double targetArea = 0.0;
    public static boolean visionTargetSeen = false;  

    /**
     * Command used for teleop control specific to the intake system
     */
    public TeleopAlignWithTape() {
        requires(Robot.visionSystem);
        requires(Robot.driveSystem);

    }

    @Override
    protected void initialize() {
        // Set up vision system to track TAPE
        Robot.visionSystem.setupSearchForTape();
        Robot.driveSystem.pidXvalue.resetPID();
        Robot.driveSystem.pidAvalue.resetPID();
        Robot.driveSystem.pidXvalue.setPIDParameters(0.0007, 0.0, 0.0, 0.0);
        Robot.driveSystem.pidAvalue.setPIDParameters(0.0003, 0.0, 0.0, 0.0);
    }

    @Override
    protected void execute() {
        // Process the frame currently sitting in VisionSystem
        Robot.visionSystem.processOneFrame();

        if (Robot.visionSystem.visionTargetSeen) {
            // I see a target! 
            double centerX = Robot.visionSystem.centerX;
            double centerX2 = Robot.visionSystem.centerX2;
            double targetArea = Robot.visionSystem.targetArea;
            double visionLeft;
            double visionRight;

            // Interpret location(s) of target and drive accordingly
            double pidOutputXvalue = Robot.driveSystem.pidXvalue.calculateOutput(VisionSystem.tapeDesiredX , ((centerX + centerX2) / 2));
            double pidOutputAvalue = Robot.driveSystem.pidAvalue.calculateOutput(VisionSystem.tapeDesiredTargetArea, targetArea);
                visionLeft = pidOutputXvalue + pidOutputAvalue;
                visionRight = -pidOutputXvalue + pidOutputAvalue;
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
