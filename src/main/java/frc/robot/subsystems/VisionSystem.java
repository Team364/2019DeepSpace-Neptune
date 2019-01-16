package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.TestPipelineTape;
import frc.robot.commands.teleop.TeleopVisionCommand;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.first.wpilibj.vision.VisionThread;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;


public class VisionSystem extends Subsystem {

  public static VisionPipeline vision;

  public static VisionThread visionThread;
  public static double centerX = 0.0;
  public static double targetArea = 0.0;
  public static boolean visionTargetSeen = false;
  
	
	private final Object imgLock = new Object();

    public UsbCamera camera;
    /**
     * VisionSystem()
     * interprets data from grip pipelines filtering images from the usb camera 
     */
    public VisionSystem() {
        camera = CameraServer.getInstance().startAutomaticCapture("Video", 0);
        camera.setResolution(320, 240);
        
        visionThread = new VisionThread(camera, new TestPipelineTape(), pipeline -> {
            if (!pipeline.filterContoursOutput().isEmpty()) {
              visionTargetSeen = true;
                Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
                synchronized (imgLock) {
                    centerX = r.x + (r.width / 2);
                    targetArea = r.area();
                }
            }else{
              visionTargetSeen = false;
            }
        });
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopVisionCommand());
    }
  

}