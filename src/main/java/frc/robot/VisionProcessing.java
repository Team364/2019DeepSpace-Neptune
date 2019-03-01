package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionProcessing {

    
    private static VisionProcessing Instance;
    // Network Table
    private NetworkTableInstance roboRioInstance;
    private NetworkTable visionParameters;
    // Network Table Input entries from Raspberry Pi
    private NetworkTableEntry visibleTargets_centerX;
    private NetworkTableEntry visibleTargets_timestamp;
    // private NetworkTableEntry visibleTargets_angle;
    // private NetworkTableEntry visibleTargets_width;
    // private NetworkTableEntry visibleTargets_height;
    // private NetworkTableEntry visibleTargets_centerY;

    VisionProcessing() {
        //Init Network Table
        roboRioInstance = NetworkTableInstance.getDefault();
        visionParameters = roboRioInstance.getTable("visionParameters");

        // Input entries
        visibleTargets_centerX = visionParameters.getEntry("visibleTargets.centerX");
        visibleTargets_timestamp = visionParameters.getEntry("visibleTargets.timeStamp");

        // visibleTargets_angle = visionParameters.getEntry("visibleTargets.angle");
        // visibleTargets_width = visionParameters.getEntry("visibleTargets.width");
        // visibleTargets_height = visionParameters.getEntry("visibleTargets.height");
        // visibleTargets_centerY = visionParameters.getEntry("visibleTargets.centerY");

      //  roboRioInstance.startClientTeam(364);

    }
    public synchronized static VisionProcessing getInstance() {
        if (Instance == null) {
            Instance = new VisionProcessing();
        }
        return Instance;
    }
    /**
     * getCenterXValues()
     * <p>Network Table Entry from Raspberry Pi
     * @return Rotated CenterX Array
     */
    public double[] getCenterXValues() {
        double[] defaultValue = {0.0};   
        if(visibleTargets_centerX.getDoubleArray(defaultValue).length > 0){
            return visibleTargets_centerX.getDoubleArray(defaultValue);
        }else{
            return defaultValue;
        }
    
    }

    public double getTimeStamp(){
        return visibleTargets_timestamp.getDouble(0.0);
    }
}
