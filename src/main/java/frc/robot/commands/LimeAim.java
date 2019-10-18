package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;
import frc.robot.misc.math.Rotation2;
import frc.robot.misc.math.Vector2;


public class LimeAim extends Command {

    NetworkTable table; 

    Vector2 translation;
    double rotation;

    double forward;
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
        forward = -Neptune.oi.controller.getRawAxis(1);
        xValue = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        areaValue = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

        double closest = Neptune.driveTrain.closestGyroSetPoint();
        rotation = xValue/29.8*0.8;//constraint(closest/40, 0.5)
        translation = new Vector2(forward, xValue/29.8);

        translation = translation.rotateBy(Rotation2.fromDegrees(Neptune.elevator.getGyro()).inverse());
        SmartDashboard.putNumber("gyro SetPoint ", xValue/29.8);

        Boolean calibrationMode = false;
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
