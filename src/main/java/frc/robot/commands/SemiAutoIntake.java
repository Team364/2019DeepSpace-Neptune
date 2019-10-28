package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.misc.math.Rotation2;
import frc.robot.misc.math.Vector2;
import static frc.robot.RobotMap.*;

public class SemiAutoIntake extends Command {

    private Vector2 translation;
    private double rotation;

    private double forward;
    private double strafe;
    private double gyroPid;
    private PIDController snapController;
    private double GyroVar;


    public SemiAutoIntake() {
        this.GyroVar = INTAKEGYROSET[0];
        requires(Neptune.driveTrain);

        snapController = new PIDController(0.03, 0, 0, new PIDSource() {
        
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
        snapController.setOutputRange(-1, 1);
        snapController.setContinuous(true);

    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        forward = -Neptune.oi.controller.getRawAxis(1) *0.50;
        strafe = Neptune.oi.controller.getRawAxis(0);

        snapController.setSetpoint(GyroVar);
        translation = new Vector2(forward, strafe);
        rotation = -gyroPid;

        Neptune.driveTrain.holonomicDrive(translation, rotation, true);
        Neptune.driveTrain.updateKinematics();
    }


    @Override
    protected void end() {
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
