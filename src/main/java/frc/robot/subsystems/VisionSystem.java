package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.DynamicVisionPipeline;
import frc.robot.commands.teleop.TeleopAlignWithTape;
import frc.robot.BasicVisionPipeline;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;//edu.wpi.first.wpilibj.CameraServer; (deprecated)
import edu.wpi.first.vision.VisionThread; //edu.wpi.first.wpilibj.vision.VisionThread; (deprecated)

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.*;
import java.util.ArrayList;

public class VisionSystem extends Subsystem {

    //public static VisionPipeline vision;
    private static DynamicVisionPipeline imageProcessingPipeline = new DynamicVisionPipeline();
    private static BasicVisionPipeline imageCapturePipeline = new BasicVisionPipeline();
    public static VisionThread imageCaptureThread;

    public double centerX = 0.0;
    public static double DesiredX = 120.0;
    public double targetArea = 0.0;
    public static double DesiredTargetArea = 800.0;
    public boolean visionTargetSeen = false;
    private final Object imgLock = new Object();
    public UsbCamera camera;

    private Mat lastCapturedImage;

    /**
     * VisionSystem() interprets data from grip pipelines filtering images from the
     * usb camera
     */
    public VisionSystem() {
        camera = CameraServer.getInstance().startAutomaticCapture("Video", 0);
        camera.setResolution(320, 240);

        // Image thread is used only to load pictures into our main thread. No processing
        // takes place within the imageCapturePipeline.
        imageCaptureThread = new VisionThread(camera, imageCapturePipeline, pipelineListener -> {
                synchronized (imgLock) {
                    // transfer the image from pipeline to main thread
                    lastCapturedImage = pipelineListener.getCapturedImage();
                }
        });
        imageCaptureThread.start();
    }

    public void setupSearchForBall() {
        // TODO: Determine best parameters for BALL, use them in the functions below
        //imageProcessingPipeline.setFilterContours(...); // Use BALL parameters here
        //imageProcessingPipeline.setHsvThresholdParameters(...); // and here
    }
    public void setupSearchForDisk() {
        // TODO: Determine best parameters for DISK, use them in the functions below
        //imageProcessingPipeline.setFilterContours(...); // Use DISK parameters here
        //imageProcessingPipeline.setHsvThresholdParameters(...); // and here
    }
    public void setupSearchForTape() {
        // TODO: Determine best parameters for TAPE, use them in the functions below
        //imageProcessingPipeline.setFilterContours(...); // Use TAPE parameters here
        //imageProcessingPipeline.setHsvThresholdParameters(...); // and here
    }

    //public ArrayList<MatOfPoint> processOneFrame() {
    public void processOneFrame() {
        Mat source;
        synchronized (imgLock) {
            // Copy image for processing - this allows lastCapturedImage to be updated in background
            source = lastCapturedImage;
        }
        imageProcessingPipeline.process(source);

        if (!imageProcessingPipeline.filterContoursOutput().isEmpty()) {
            visionTargetSeen = true;
            Rect r = Imgproc.boundingRect(imageProcessingPipeline.filterContoursOutput().get(0));
            synchronized (imgLock) {
                centerX = r.x + (r.width / 2);
                targetArea = r.area();
            }
        } else {
            visionTargetSeen = false;
        }
    }
    protected void initDefaultCommand() {
        //setDefaultCommand(new TeleopVisionCommand());
    }

    public boolean reachedDesiredX(){
        return (centerX <= (DesiredX + 2.5) && centerX >= (DesiredX - 2.5));
    }
    public boolean reachedDesiredTargetArea(){
        return (targetArea <= (DesiredTargetArea + 100) && targetArea >= (DesiredTargetArea - 100));
    }

}