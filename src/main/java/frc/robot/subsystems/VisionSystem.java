package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.teleop.*;
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

    // Printing array of objects 
    public double centerX = 0.0;
    public double centerX2 = 0.0;
    public double centerX3 = 0.0;
    public double centerX4 = 0.0;
    public double centerX5 = 0.0;
    public double centerX6 = 0.0;

    public double targetArea = 0.0;
    public double targetArea2 = 0.0;
    public double targetArea3 = 0.0;
    public double targetArea4 = 0.0;
    public double targetArea5 = 0.0;
    public double targetArea6 = 0.0;

    //Pid SetPoints
    public double DesiredX;
    public static double tapeDesiredX = 194;
    public static double tapeDesiredTargetArea = 2000.0;

    public static double ballDesiredX = 170;
    public static double ballDesiredTargetArea = 17000.0;

    public static double diskDesiredX = 25;


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

    //Tape HSV Values
    private double[] tapeThresholdHue = {71.22302158273381, 100.13651877133105};
    private double[] tapeThresholdSaturation = {22.93165467625899, 107.04778156996588};
    private double[] tapeThresholdValue = {240.78237410071944, 255.0};

    //Ball Filter Values
    double ballContoursMinArea = 180.0;
    double ballContoursMinPerimeter = 200.0;
    double ballContoursMinWidth = 0.0;
    double ballContoursMaxWidth = 1000.0;
    double ballContoursMinHeight = 0.0;
    double ballContoursMaxHeight = 1000.0;
    double[] ballContoursSolidity = {0.0, 100.0};
    double ballContoursMaxVertices = 1000000.0;
    double ballContoursMinVertices = 0.0;
    double ballContoursMinRatio = 0.0;
    double ballContoursMaxRatio = 1000.0;

    //Ball HSV Values
    double[] ballThresholdHue = {0.16709213274541646, 23.428933171859523};
    double[] ballThresholdSaturation = {105.48561151079136, 255.0};
    double[] ballThresholdValue = {6.879496402877698, 255.0};
    
    //Disk Filter Values
    double diskContoursMinArea = 0.0;
    double diskContoursMinPerimeter = 200.0;
    double diskContoursMinWidth = 0.0;
    double diskContoursMaxWidth = 1000.0;
    double diskContoursMinHeight = 0.0;
    double diskContoursMaxHeight = 100.0;
    double[] diskContoursSolidity = {0.0, 100.0};
    double diskContoursMaxVertices = 1000000.0;
    double diskContoursMinVertices = 0.0;
    double diskContoursMinRatio = 0.0;
    double diskContoursMaxRatio = 1000.0;

    //Disk HSV Values
    double[] diskThresholdHue = {22.66187050359712, 37.16723549488056};
    double[] diskThresholdSaturation = {121.53776978417264, 255.0};
    double[] diskThresholdValue = {133.00359712230215, 255.0};

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
        
    }
    public void setupSearchForDisk() {
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
            Rect a = Imgproc.boundingRect(imageProcessingPipeline.filterContoursOutput().get(0));
      
            synchronized (imgLock) {
                centerX = a.x + (a.width / 2);
                targetArea = a.area();
                if(imageProcessingPipeline.filterContoursOutput().size() >= 2){
                    Rect b =Imgproc.boundingRect(imageProcessingPipeline.filterContoursOutput().get(1));
                    centerX2 = b.x + (b.width / 2);
                    targetArea2 = b.area();
                }
                if(imageProcessingPipeline.filterContoursOutput().size() >= 3){
                    Rect c =Imgproc.boundingRect(imageProcessingPipeline.filterContoursOutput().get(2));
                    centerX3 = c.x + (c.width / 2);
                    targetArea3 = c.area();
                }
                if(imageProcessingPipeline.filterContoursOutput().size() >= 4){
                    Rect d =Imgproc.boundingRect(imageProcessingPipeline.filterContoursOutput().get(3));
                    centerX4 = d.x + (d.width / 2);
                    targetArea4 = d.area();
                }
                if(imageProcessingPipeline.filterContoursOutput().size() >= 5){
                    Rect e =Imgproc.boundingRect(imageProcessingPipeline.filterContoursOutput().get(4));
                    centerX5 = e.x + (e.width / 2);
                    targetArea5 = e.area();
                }
                if(imageProcessingPipeline.filterContoursOutput().size() >= 6){
                    Rect f =Imgproc.boundingRect(imageProcessingPipeline.filterContoursOutput().get(5));
                    centerX6 = f.x + (f.width / 2);
                    targetArea6 = f.area();
                }
            }
           // System.out.println(imageProcessingPipeline.filterContoursOutput().size());

          
        } else {
            visionTargetSeen = false;
        }
    }
    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopBasicVisionCommand());
    }

    public boolean reachedDesiredX(){
        return (((centerX + centerX2) / 2) <= (tapeDesiredX + 5) && ((centerX + centerX2) / 2)>= (tapeDesiredX - 5));
    }
    public boolean reachedDesiredTargetArea(){
        return (targetArea <= (tapeDesiredTargetArea + 100) && targetArea >= (tapeDesiredTargetArea - 100));
    }
    public boolean moreThan2Targets(){
        if(imageProcessingPipeline.filterContoursOutput().size() >= 3){
            return true;
        }else{
            return false;
        }
    }
    public boolean alignedWithDisk(){
        return ((centerX / 2) <= (diskDesiredX + 6) && (centerX / 2)>= (diskDesiredX - 6));
    }
}