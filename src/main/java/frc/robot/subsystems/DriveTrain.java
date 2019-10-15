package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Neptune;
import frc.robot.defaultcommands.DrivetrainCommand;

import static frc.robot.RobotMap.*;
import static frc.robot.Neptune.*;

public class Drivetrain extends Subsystem {

    public static Drivetrain Instance = null;
	/*
	 * 0 is Front Right
	 * 1 is Front Left
	 * 2 is Back Left
	 * 3 is Back Right
	 */
    private SwerveMod[] mSwerveModules;
    private int w = WHEELBASE;
    private int t = TRACKWIDTH;
    private Command zero;
    public double[] lastTargetAngle = new double[mSwerveModules.length];

    public Drivetrain() {
            mSwerveModules = new SwerveMod[] {
                    new SwerveMod(0,
                            new TalonSRX(FRANGLE),
                            new TalonSRX(FRDRIVE),
                            MOD0OFFSET),
                    new SwerveMod(1,
                            new TalonSRX(FLANGLE),
                            new TalonSRX(FLDRIVE),
                            MOD1OFFSET),
                    new SwerveMod(2,
                            new TalonSRX(BLANGLE),
                            new TalonSRX(BLDRIVE),
                            MOD2OFFSET),
                    new SwerveMod(3,
                            new TalonSRX(BRANGLE),
                            new TalonSRX(BRDRIVE),
                            MOD3OFFSET)
            };

            mSwerveModules[0].setDriveInverted(true);
            mSwerveModules[2].setDriveInverted(true);
            mSwerveModules[3].setDriveInverted(true);
            //mSwerveModules[2].setSensorPhase(true);

            zero = new Offset(mSwerveModules);
            zero.start();
    }  
    
    
  public synchronized static Drivetrain getInstance() {
    if (Instance == null) {
      Instance = new Drivetrain();
    }
    return Instance;
  }


    public double[] calculateSwerveModuleAngles(double forward, double strafe, double rotation) {
            double angleRad = Math.toRadians(Neptune.elevator.getYaw());
            double temp = forward * Math.cos(angleRad) + strafe * Math.sin(angleRad);
            strafe = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
            forward = temp;
    
        double a = strafe - rotation * w / t;
        double b = strafe + rotation * w / t;
        double c = forward - rotation * t / w;
        double d = forward + rotation * t / w;

        return new double[]{
                Math.atan2(b, c) * 180 / Math.PI,
                Math.atan2(b, d) * 180 / Math.PI,
                Math.atan2(a, d) * 180 / Math.PI,//ad
                Math.atan2(a, c) * 180 / Math.PI
        };
    }

    public SwerveMod getSwerveModule(int i) {
        return mSwerveModules[i];
    }

    public void holonomicDrive(double forward, double strafe, double rotation) {

            double angleRad = Math.toRadians(Neptune.elevator.getYaw());
            double temp = forward * Math.cos(angleRad) + strafe * Math.sin(angleRad);
            strafe  = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
            forward = temp;

        double a = strafe - rotation * w / t;
        double b = strafe + rotation * w / t;
        double c = forward - rotation * t / w;
        double d = forward + rotation * t / w;

        double[] angles = new double[]{
                Math.atan2(b, c) * 180 / Math.PI,
                Math.atan2(b, d) * 180 / Math.PI,
                Math.atan2(a, d) * 180 / Math.PI,
                Math.atan2(a, c) * 180 / Math.PI
        };

        double[] speeds = new double[]{
                Math.sqrt(b * b + c * c),
                Math.sqrt(b * b + d * d),
                Math.sqrt(a * a + d * d),
                Math.sqrt(a * a + c * c)
        };

        double max = speeds[0];

        for (double speed : speeds) {
            if (speed > max) {
                max = speed;
            }
        }

        for (int i = 0; i < 4; i++) {
            if (Math.abs(forward) > STICKDEADBAND || Math.abs(strafe) > STICKDEADBAND || Math.abs(rotation) > STICKDEADBAND) {
                mSwerveModules[i].setTargetAngle(angles[i]);
                lastTargetAngle[i] = (angles[i]);
            } 
            else{
                mSwerveModules[i].setTargetAngle(lastTargetAngle[i]);
            }
            mSwerveModules[i].setTargetSpeed(speeds[i]);
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

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DrivetrainCommand(this));
    }
}
