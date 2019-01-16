package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.TestPipelineTape;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.first.wpilibj.vision.VisionThread;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class TeleopVisionCommand extends Command {
    public static VisionPipeline vision;

    public static VisionThread visionThread;
    public static double centerX = 0.0;
    public static double targetArea = 0.0;
    public static boolean visionTargetSeen = false;
    
      
      private final Object imgLock = new Object();

    /**
     * Command used for teleop control specific to the intake system
     */
    public TeleopVisionCommand() {
        requires(Robot.visionSystem);
    }

    @Override
    protected void initialize() {
        Robot.visionSystem.visionThread.start();

    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

}
