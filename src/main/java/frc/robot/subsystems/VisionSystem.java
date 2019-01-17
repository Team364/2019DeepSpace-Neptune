package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.DynamicVisionPipeline;
import frc.robot.commands.teleop.TeleopAlignWithTape;
import frc.robot.BasicVisionPipeline;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.vision.VisionThread;

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

    //Tape Fliter Values
    private double tapeContoursMinArea = 100.0;
    private double tapeContoursMinPerimeter = 0.0;
    private double tapeContoursMinWidth = 0.0;
    private double tapeContoursMaxWidth = 999.0;
    private double tapeContoursMinHeight = 0.0;
    private double tapeContoursMaxHeight = 1000.0;
    private double[] tapeContoursSolidity = {0.0, 100.0};
    private double tapeContoursMaxVertices = 1000000.0;
    private double tapeContoursMinVertices = 0.0;
    private double tapeContoursMinRatio = 0.0;
    private double tapeContoursMaxRatio = 1000.0;

    //Tape Contour Values
    private double[] tapeThresholdHue = {71.22302158273381, 100.13651877133105};
    private double[] tapeThresholdSaturation = {22.93165467625899, 107.04778156996588};
    private double[] tapeThresholdValue = {240.78237410071944, 255.0};
    

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
                /*        
        imageProcessingPipeline.setFilterContours(
            ballContoursMinArea,
            ballContoursMinPerimeter,
            ballContoursMinWidth,
            ballContoursMaxWidth,
            ballContoursMinHeight,
            ballContoursMaxHeight,
            ballContoursSolidity,
            ballContoursMaxVertices,
            ballContoursMinVertices, 
            ballContoursMinRatio, 
            ballContoursMaxRatio
            );
        imageProcessingPipeline.setHsvThresholdParameters(
            ballThresholdHue,
            ballThresholdSaturation,
            ballThresholdValue
        );
        */
    }
    public void setupSearchForDisk() {
        /*        
        imageProcessingPipeline.setFilterContours(
            diskContoursMinArea,
            diskContoursMinPerimeter,
            diskContoursMinWidth,
            diskContoursMaxWidth,
            diskContoursMinHeight,
            diskContoursMaxHeight,
            diskContoursSolidity,
            diskContoursMaxVertices,
            diskContoursMinVertices, 
            diskContoursMinRatio, 
            diskContoursMaxRatio
            );
        imageProcessingPipeline.setHsvThresholdParameters(
            diskThresholdHue,
            diskThresholdSaturation,
            diskThresholdValue
        );
        */
    }
    public void setupSearchForTape() {
        imageProcessingPipeline.setFilterContours(
            tapeContoursMinArea,
            tapeContoursMinPerimeter,
            tapeContoursMinWidth,
            tapeContoursMaxWidth,
            tapeContoursMinHeight,
            tapeContoursMaxHeight,
            tapeContoursSolidity,
            tapeContoursMaxVertices,
            tapeContoursMinVertices, 
            tapeContoursMinRatio, 
            tapeContoursMaxRatio
            );
        imageProcessingPipeline.setHsvThresholdParameters(
            tapeThresholdHue,
            tapeThresholdSaturation,
            tapeThresholdValue
        );
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