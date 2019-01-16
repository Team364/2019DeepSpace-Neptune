package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
//import frc.robot.DynamicVisionPipeline;
//import edu.wpi.first.wpilibj.vision.VisionPipeline;
//import edu.wpi.first.wpilibj.vision.VisionThread;
//import org.opencv.core.Rect;
//import org.opencv.imgproc.Imgproc;

public class TeleopAlignWithTape extends Command {
    //public static VisionPipeline vision;

    //public static VisionThread visionThread;
    public static double centerX = 0.0;
    public static double targetArea = 0.0;
    public static boolean visionTargetSeen = false;  
    private static boolean robotAligned = false;

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
    }

    @Override
    protected void execute() {
        // Process the frame currently sitting in VisionSystem
        Robot.visionSystem.processOneFrame();

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

        // How do we know if it's finished? Setting to true for now...
        // TODO: include logic to determine if we've "lined up with tape"
        robotAligned = true;

        //return false; // Be careful with returning false... this would cause the command to never end
        return robotAligned;
    }

    @Override
    protected void end() {
    }

}
