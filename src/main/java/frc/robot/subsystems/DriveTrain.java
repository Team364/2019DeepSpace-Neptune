package frc.robot.subsystems;

import static frc.robot.Conversions.deadband;
import static frc.robot.RobotMap.*;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Neptune;
import frc.robot.commands.DrivetrainCommand;
import frc.robot.misc.math.Rotation2;
import frc.robot.misc.math.Vector2;
 

public class Drivetrain extends Subsystem {

    private static Drivetrain Instance = null;
	/*
	 * 0 is Front Right
	 * 1 is Front Left
	 * 2 is Back Left
	 * 3 is Back Right
	 */
    private SwerveMod[] mSwerveModules;


    public Drivetrain() {
            mSwerveModules = new SwerveMod[] {
                    new SwerveMod(0,
                            new Vector2(-TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                            new TalonSRX(FRANGLE),
                            new AnalogInput(FRENCODER),
                            new TalonSRX(FRDRIVE),
                            false, 
                            false,
                            MOD0OFFSET),
                    new SwerveMod(1,
                            new Vector2(TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                            new TalonSRX(FLANGLE),
                            new AnalogInput(FLENCODER),
                            new TalonSRX(FLDRIVE),
                            true,
                            true,
                            MOD1OFFSET),
                    new SwerveMod(2,
                            new Vector2(TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
                            new TalonSRX(BLANGLE),
                            new AnalogInput(BLENCODER),
                            new TalonSRX(BLDRIVE),
                            true,
                            false,
                            MOD2OFFSET),
                    new SwerveMod(3,
                            new Vector2(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
                            new TalonSRX(BRANGLE),
                            new AnalogInput(BRENCODER),
                            new TalonSRX(BRDRIVE),
                            true,
                            false,
                            MOD3OFFSET)
            };
         
            
    } 

    public synchronized static Drivetrain getInstance() {
        if (Instance == null) {
          Instance = new Drivetrain();
        }
        return Instance;
      }


    public SwerveMod getSwerveModule(int i) {
        return mSwerveModules[i];
    }

    public void holonomicDrive(Vector2 translation, double rotation, boolean speed) {
            Vector2 velocity;
            for(SwerveMod mod : getSwerveModules()){
                Vector2 newTranslation = null;
                newTranslation = translation.rotateBy(Rotation2.fromDegrees(Neptune.elevator.getGyro()));

                velocity = mod.getModulePosition().normal().scale(deadband(rotation)).add(newTranslation);
                mod.setTargetVelocity(velocity, speed, rotation);
            }        
    }
    public void updateKinematics(){
        for (SwerveMod mod : getSwerveModules()){
            mod.setTargetAngle(mod.targetAngle);
            mod.setTargetSpeed(mod.targetSpeed);
        }
    }

    public void stopDriveMotors() {
        for (SwerveMod module : mSwerveModules) {
            module.setTargetSpeed(0);
        }
    }

    public SwerveMod[] getSwerveModules() {
        return mSwerveModules;
    }

    public double closestGyroSetPoint(double[] gyroSet){
        double checkPoint = 0;
        double returnSetPoint = 0;
        for(Double setPoint : gyroSet){
            double initial = setPoint - Neptune.elevator.getFittedYaw();
            if(Math.abs(initial) < Math.abs(checkPoint) || checkPoint == 0) {
                checkPoint = initial;
                returnSetPoint = setPoint;
            }
        }
        return returnSetPoint;
    }

    public void setTrackingMode(){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3); //Turns LED off
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0); //Begin Processing Vision
    }

    public void setDriverCamMode(){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1); //Turns LED off
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1); //Disable Vision Processing
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DrivetrainCommand(this));
    }
    
}
