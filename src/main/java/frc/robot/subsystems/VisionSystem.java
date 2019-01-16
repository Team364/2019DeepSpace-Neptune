package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.DynamicVisionPipeline;
//import frc.robot.commands.teleop.TeleopVisionCommand;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;//edu.wpi.first.wpilibj.CameraServer; (deprecated)

//import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.first.vision.VisionThread; //edu.wpi.first.wpilibj.vision.VisionThread; (deprecated)
import edu.wpi.first.vision.VisionRunner;

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

    private VisionRunner visionRunner;
    private boolean imageReady = false;

    public void setupSearchForBall() {
        // TODO: Determine best parameters for BALL, use them in the functions below
        //visionPipeline.setFilterContours(...); // Use BALL parameters here
        //visionPipeline.setHsvThresholdParameters(...); // and here
    }
    public void setupSearchForDisk() {
        // TODO: Determine best parameters for DISK, use them in the functions below
        //visionPipeline.setFilterContours(...); // Use DISK parameters here
        //visionPipeline.setHsvThresholdParameters(...); // and here
    }
    public void setupSearchForTape() {
        // TODO: Determine best parameters for TAPE, use them in the functions below
        //visionPipeline.setFilterContours(...); // Use TAPE parameters here
        //visionPipeline.setHsvThresholdParameters(...); // and here
    }

    public void beginSearch() {
        // Set up and start the image processing thread
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

        visionThread.start();
    }

    public void stopSearch() {
        // visionThread.interrupt();
        // visionThread.
        //visionThread.setDaemon();
    }

    /**
     * VisionSystem() interprets data from grip pipelines filtering images from the
     * usb camera
     */
    public VisionSystem() {
        camera = CameraServer.getInstance().startAutomaticCapture("Video", 0);
        camera.setResolution(320, 240);

        //visionRunner = new VisionRunner(camera, visionPipeline, pipelineListener->{imageReady = true;});

        // TODO: Determine the best place to start/stop threads... OR, is a thread even necessary?
        // I suspect that a thread is NOT necessary.. we should be able to take a snapshot at any
        // point in time (e.g., when making a decision about robot movement) and filter the image
        // at that time (rather than having it always run in the background)

        // OPTION 1: Run the code below within our vision system
        // OPTION 2: Run the code below in the initialization for our "tracking" commands
        /*visionThread = new VisionThread(camera, visionPipeline, pipelineListener -> {

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
        });*/
    }

    public void processOneFrame() {
        while (!imageReady) visionRunner.runOnce();
    }
    protected void initDefaultCommand() {
        //setDefaultCommand(new TeleopVisionCommand());
    }

}