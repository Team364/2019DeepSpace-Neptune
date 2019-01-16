package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.DynamicVisionPipeline;
//import frc.robot.commands.teleop.TeleopVisionCommand;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

//import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.first.vision.VisionThread; //edu.wpi.first.wpilibj.vision.VisionThread; (deprecated)

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class VisionSystem extends Subsystem {

    //public static VisionPipeline vision;

    private static DynamicVisionPipeline visionPipeline = new DynamicVisionPipeline();
    public static VisionThread visionThread;
    public double centerX = 0.0;
    public double targetArea = 0.0;
    public boolean visionTargetSeen = false;
    private final Object imgLock = new Object();
    public UsbCamera camera;

    public void setupSearchForBall() {
        //visionPipeline.setFilterContours(...); // Use BALL parameters here
        //visionPipeline.setHsvThresholdParameters(...); // and here
    }
    public void setupSearchForDisk() {
        //visionPipeline.setFilterContours(...); // Use DISK parameters here
        //visionPipeline.setHsvThresholdParameters(...); // and here
    }
    public void setupSearchForTape() {
        //visionPipeline.setFilterContours(...); // Use TAPE parameters here
        //visionPipeline.setHsvThresholdParameters(...); // and here
    }

    /**
     * VisionSystem() interprets data from grip pipelines filtering images from the
     * usb camera
     */
    public VisionSystem() {
        camera = CameraServer.getInstance().startAutomaticCapture("Video", 0);
        camera.setResolution(320, 240);

        // OPTION 1: Run the code below within our vision system
        // OPTION 2: Run the code below in the initialization for our "tracking" commands
        visionThread = new VisionThread(camera, visionPipeline, pipelineListener -> {

            if (!pipelineListener.filterContoursOutput().isEmpty()) {
                visionTargetSeen = true;
                Rect r = Imgproc.boundingRect(pipelineListener.filterContoursOutput().get(0));
                synchronized (imgLock) {
                    centerX = r.x + (r.width / 2);
                    targetArea = r.area();
                }
            } else {
                visionTargetSeen = false;
            }
        });
    }

    protected void initDefaultCommand() {
        //setDefaultCommand(new TeleopVisionCommand());
    }

}