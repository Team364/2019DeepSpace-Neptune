package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.States;
import frc.robot.misc.math.Vector2;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.misc.math.Rotation2;
import static frc.robot.Conversions.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class LimeAim extends Command {

    NetworkTable table; 

    Vector2 translation;
    double rotation;

    double xValue;
    double areaValue;
    double skewValue;

    public LimeAim() {
        requires(Neptune.driveTrain);
    }

    @Override
    protected void initialize() {
        Neptune.driveTrain.setTrackingMode();
    }

    @Override
    protected void execute() {
        xValue = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        areaValue = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
        //figure out if 10 is right
        rotation = Neptune.driveTrain.closestGyroSetPoint()/10;
        translation = new Vector2(constraint(areaValue - 11.5) * -0.25, xValue/29.8);
        translation = translation.rotateBy(Rotation2.fromDegrees(Neptune.elevator.getGyro()).inverse());

        Boolean calibrationMode = true;
        if(!calibrationMode){
        Neptune.driveTrain.holonomicDrive(translation, rotation, true);
        Neptune.driveTrain.updateKinematics();
        }
    }


    @Override
    protected void end() {
        Neptune.driveTrain.setDriverCamMode();
        Vector2 none = Vector2.ZERO;
        Neptune.driveTrain.holonomicDrive(none, 0, false);
        Neptune.driveTrain.updateKinematics();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }
}
