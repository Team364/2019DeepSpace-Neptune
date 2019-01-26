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
    //NetworkTableEntry xValue;
    //Input entries
    NetworkTableEntry visibleTargets_angle;
    NetworkTableEntry visibleTargets_width;
    NetworkTableEntry visibleTargets_height;
    NetworkTableEntry visibleTargets_centerX;
    NetworkTableEntry visibleTargets_centerY;
    NetworkTableEntry visibleTargets_noTarget;
    //Outpu entries
    NetworkTableEntry configNumber;

    /**
     * VisionSystem() interprets data from grip pipelines filtering images from the
     * usb camera
     */
    public VisionSystem() {
        roboRioInstance = NetworkTableInstance.getDefault();
        visionParameters = roboRioInstance.getTable("visionParameters");
        //xValue = visionParameters.getEntry("xValue");
        //Input entries
        visibleTargets_angle = visionParameters.getEntry("visibleTargets.angle");
        visibleTargets_width = visionParameters.getEntry("visibleTargets.width");
        visibleTargets_height = visionParameters.getEntry("visibleTargets.height");
        visibleTargets_centerX = visionParameters.getEntry("visibleTargets.centerX");
        visibleTargets_centerY = visionParameters.getEntry("visibleTargets.centerY");
        visibleTargets_noTarget = visionParameters.getEntry("visibleTargets.noTarget");

        //Output Entries
        configNumber = visionParameters.getEntry("searchConfigNumber");

        roboRioInstance.startClientTeam(364);
    }
    public double[] getAngleValues() {
        double[] defaultValue = {0.0};     
        return visibleTargets_angle.getDoubleArray(defaultValue);
    }
    public double[] getWidthValues() {
        double[] defaultValue = {0.0};     
        return visibleTargets_width.getDoubleArray(defaultValue);
    }
    public double[] getHeightValues() {
        double[] defaultValue = {0.0};     
        return visibleTargets_height.getDoubleArray(defaultValue);
    }
    public double[] getCenterXValues() {
        double[] defaultValue = {0.0};     
        return visibleTargets_centerX.getDoubleArray(defaultValue);
    }
    public double[] getCenterYValues() {
        double[] defaultValue = {0.0};     
        return visibleTargets_centerY.getDoubleArray(defaultValue);
    }
    public boolean noTarget(){
        boolean defaultValue = true;
        return  visibleTargets_noTarget.getBoolean(defaultValue);
    }

    // public double getTargetXValue(){
    //     double defaultValue = 0;
    //     return xValue.getDouble(defaultValue);
    // }

    public void setupSearchForBall() {     
        configNumber.setDouble(1);
    }
    public void setupSearchForDisk() {
        configNumber.setDouble(2);
    }
    public void setupSearchForTape() {
        configNumber.setDouble(0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopBasicVisionCommand());
    }

}
