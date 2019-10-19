package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
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
    double gyroPid;
    double strafePid;
    PIDController snapController;
    PIDController strafeController;
    double[] GyroSet;
    double closestSetPoint;


    public LimeAim(double[] GyroSet) {
        this.GyroSet = GyroSet;
        requires(Neptune.driveTrain);
        snapController = new PIDController(0.03, 0.0, 0.0, new PIDSource() {
        
        public void setPIDSourceType(PIDSourceType pidSource){
        }
     
        public PIDSourceType getPIDSourceType(){
            return PIDSourceType.kDisplacement;
        }

        public double pidGet() {
            return Neptune.elevator.getFittedYaw();
        }

    }, output -> {
        gyroPid = output;
    });
    snapController.enable();
    snapController.setInputRange(0, 360);
    snapController.setOutputRange(-0.3, 0.3);
    snapController.setContinuous(true);


    strafeController = new PIDController(0.04, 0.00, 0, new PIDSource() {
        
        public void setPIDSourceType(PIDSourceType pidSource){
        }

        public PIDSourceType getPIDSourceType(){
            return PIDSourceType.kDisplacement;
        }

        public double pidGet() {
            xValue = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
            return xValue;
        }

    }, output -> {
        strafePid = output;
    });
    strafeController.enable();
    strafeController.setInputRange(-29.8, 29.8);
    strafeController.setOutputRange(-0.3, 0.3);
    strafeController.setContinuous(true);
    }

    @Override
    protected void initialize() {
        closestSetPoint = Neptune.driveTrain.closestGyroSetPoint(GyroSet);
        Neptune.driveTrain.setTrackingMode();
    }

    @Override
    protected void execute() {
        forward = -Neptune.oi.controller.getRawAxis(1);

        snapController.setSetpoint(closestSetPoint);
        strafeController.setSetpoint(0);
        SmartDashboard.putNumber("strafePID", strafePid);
        translation = new Vector2(forward, -strafePid);
        translation = translation.rotateBy(Rotation2.fromDegrees(Neptune.elevator.getGyro()).inverse());
        rotation = -gyroPid;
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
