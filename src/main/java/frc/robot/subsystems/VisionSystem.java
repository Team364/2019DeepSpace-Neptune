package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.commands.teleop.*;
// import frc.robot.DynamicVisionPipeline;
// import frc.robot.commands.teleop.TeleopAlignWithTape;
// import frc.robot.BasicVisionPipeline;
import frc.robot.commands.teleop.TeleopBasicVisionCommand;


// import edu.wpi.cscore.UsbCamera;
// import edu.wpi.first.cameraserver.CameraServer;
// import edu.wpi.first.vision.VisionThread;

// import org.opencv.core.Rect;
// import org.opencv.imgproc.Imgproc;
// import org.opencv.core.*;
// import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionSystem extends Subsystem {

    NetworkTableInstance roboRioInstance;
    NetworkTable visionParameters;
    NetworkTableEntry xValue;
    /**
     * VisionSystem() interprets data from grip pipelines filtering images from the
     * usb camera
     */
    public VisionSystem() {
        roboRioInstance = NetworkTableInstance.getDefault();
        visionParameters = roboRioInstance.getTable("visionParameters");
        xValue = visionParameters.getEntry("xValue");

        roboRioInstance.startClientTeam(364);
    }
    // public double[] getTargetXValues() {
    //     // THIS IS A TEST FUNCTION 
    //     double[] defaultValue = {0.0};     
    //     return xValue.getDoubleArray(defaultValue);
    // }
    public double getTargetXValue(){
        double defaultValue = 0;
        return xValue.getDouble(defaultValue);
    }

    public void setupSearchForBall() {     
    
    }
    public void setupSearchForDisk() {

    }
    public void setupSearchForTape() {

    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopBasicVisionCommand());
    }

}
