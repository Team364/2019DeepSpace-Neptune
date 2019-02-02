package frc.robot.driver.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.driver.defaultcommands.TeleopBasicVisionCommand;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionSystem extends Subsystem {

    //Network Table
    private NetworkTableInstance roboRioInstance;
    private NetworkTable visionParameters;
    //Network Table Input entries from Raspberry Pi
    private NetworkTableEntry visibleTargets_angle;
    private NetworkTableEntry visibleTargets_width;
    private NetworkTableEntry visibleTargets_height;
    private NetworkTableEntry visibleTargets_centerX;
    private NetworkTableEntry visibleTargets_centerY;
    private NetworkTableEntry visibleTargets_noTarget;
    //Output entries to Raspberry Pi
    private NetworkTableEntry configNumber;

    /**
     * VisionSystem() interprets data from grip pipelines filtering images from the
     * usb camera
     */
    public VisionSystem() {
        //Init Network Table
        roboRioInstance = NetworkTableInstance.getDefault();
        visionParameters = roboRioInstance.getTable("visionParameters");

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

    /**
     * getAngleValues()
     * <p>Network Table Entry from Raspberry Pi
     * @return Rotated Rectangle Angle Array
     */
    public double[] getAngleValues() {
        double[] defaultValue = {0.0};     
        return visibleTargets_angle.getDoubleArray(defaultValue);
    }

    /**
     * getWidthValues()
     * <p>Network Table Entry from Raspberry Pi
     * @return Rotated Rectangle Width Array
     */
    public double[] getWidthValues() {
        double[] defaultValue = {0.0};     
        return visibleTargets_width.getDoubleArray(defaultValue);
    }

    /**
     * getHeightValues()
     * <p>Network Table Entry from Raspberry Pi
     * @return Rotated Rectangle Height Array
     */
    public double[] getHeightValues() {
        double[] defaultValue = {0.0};     
        return visibleTargets_height.getDoubleArray(defaultValue);
    }

    /**
     * getCenterXValues()
     * <p>Network Table Entry from Raspberry Pi
     * @return Rotated Rectangle CenterX Array
     */
    public double[] getCenterXValues() {
        double[] defaultValue = {0.0};     
        return visibleTargets_centerX.getDoubleArray(defaultValue);
    }

    /**
     * getCenterYValues()
     * <p>Network Table Entry from Raspberry Pi
     * @return Rotated Rectangle CenterY Array
     */
    public double[] getCenterYValues() {
        double[] defaultValue = {0.0};     
        return visibleTargets_centerY.getDoubleArray(defaultValue);
    }

    /**
     * noTarget()
     * <p>Network Table Entry from Raspberry Pi
     * @return if Vision Targets are detected
     * <p>True = no targets
     * <p>False = targets are visible
     */
    public boolean noTarget(){
        boolean defaultValue = true;
        return  visibleTargets_noTarget.getBoolean(defaultValue);
    }

    /**
     * setupSearchForBall()
     * <p>Network Table Entry to Raspberry Pi
     * <p>Sets Pipeline to look for ball
     */
    public void setupSearchForBall() {     
        configNumber.setDouble(1);
    }

    /**
     * setupSearchForDisk()
     * <p>Network Table Entry to Raspberry Pi
     * <p>Sets Pipeline to look for Disk
     */
    public void setupSearchForDisk() {
        configNumber.setDouble(2);
    }

    /**
     * setupSearchForTape()
     * <p>Network Table Entry to Raspberry Pi
     * <p>Sets Pipeline to look for tape
     */
    public void setupSearchForTape() {
        configNumber.setDouble(0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopBasicVisionCommand());
    }

}
