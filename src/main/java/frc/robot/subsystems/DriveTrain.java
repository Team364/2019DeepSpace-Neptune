package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;
import frc.robot.commands.DrivetrainCommand;
import frc.robot.misc.math.Rotation2;
import frc.robot.misc.math.Vector2;

import static frc.robot.RobotMap.*;
import static frc.robot.Neptune.*;
import frc.robot.subsystems.SwerveMod.*;

public class Drivetrain extends Subsystem {

    private static Drivetrain Instance = null;
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

    public Drivetrain() {
            mSwerveModules = new SwerveMod[] {
                    new SwerveMod(0,
                            new Vector2(TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                            new TalonSRX(FRANGLE),
                            new TalonSRX(FRDRIVE),
                            MOD0OFFSET),
                    new SwerveMod(1,
                            new Vector2(-TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                            new TalonSRX(FLANGLE),
                            new TalonSRX(FLDRIVE),
                            MOD1OFFSET),
                    new SwerveMod(2,
                            new Vector2(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
                            new TalonSRX(BLANGLE),
                            new TalonSRX(BLDRIVE),
                            MOD2OFFSET),
                    new SwerveMod(3,
                            new Vector2(TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
                            new TalonSRX(BRANGLE),
                            new TalonSRX(BRDRIVE),
                            MOD3OFFSET)
            };

            mSwerveModules[0].setDriveInverted(true);
            mSwerveModules[2].setDriveInverted(true);
            mSwerveModules[3].setDriveInverted(true);            
            //mSwerveModules[1].setDriveInverted(true);

            mSwerveModules[1].setSensorPhase(true);
            //mSwerveModules[1].getAngleMotor().setInverted(false);

            setZero();
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

    public void holonomicDrive(Vector2 translation, double rotation, boolean speedOff) {

            // need to get pigeon vector
            translation = translation.rotateBy(Rotation2.fromDegrees(Neptune.elevator.getYaw()).inverse());

        for (SwerveMod mod : getSwerveModules()) {
            Vector2 velocity = mod.getModulePosition().normal().scale(rotation).add(translation);
            mod.setTargetVelocity(velocity, speedOff);
        }

    }
    public void updateKinematics(){
        for (SwerveMod mod : getSwerveModules()){
            mod.setTargetAngle(mod.targetAngle);
            mod.setTargetSpeed(mod.targetSpeed);
        }
    }
    public void setZero(){
        for(SwerveMod mod : getSwerveModules()){
            mod.getAngleMotor().set(ControlMode.Position, mod.mZeroOffset);
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
