package frc.robot.subsystems;

import static frc.robot.Conversions.*;
import static frc.robot.RobotMap.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.misc.math.Vector2;


public class SwerveMod{
    private double lastTargetAngle = 0;
    public final int moduleNumber;

    public final int mZeroOffset;

    private final TalonSRX mAngleMotor;
    private final TalonSRX mDriveMotor;
    private Vector2 modulePosition;
    private boolean driveInverted = false;
    
    public double targetAngle;
    public double targetSpeed;
    public double smartAngle;
    public Vector2 velocity;
    public double currentAngle;

    public SwerveMod(int moduleNumber, Vector2 modulePosition, TalonSRX angleMotor, TalonSRX driveMotor, boolean invertDrive, boolean invertSensorPhase, int zeroOffset) {
        this.moduleNumber = moduleNumber;
        this.modulePosition = modulePosition;
        mAngleMotor = angleMotor;
        mDriveMotor = driveMotor;
        mZeroOffset = zeroOffset;
        targetAngle = 0;
        targetSpeed = 0;
        currentAngle = 0;


        // Configure Angle Motor
        angleMotor.configFactoryDefault();
        angleMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, SLOTIDX, SWERVETIMEOUT);
        angleMotor.selectProfileSlot(SLOTIDX, SWERVETIMEOUT);
        angleMotor.setSensorPhase(invertSensorPhase);
        angleMotor.config_kP(SLOTIDX, ANGLEP, SWERVETIMEOUT);
        angleMotor.config_kI(SLOTIDX, ANGLEI, SWERVETIMEOUT);
        angleMotor.config_kD(SLOTIDX, ANGLED, SWERVETIMEOUT);
        angleMotor.setNeutralMode(NeutralMode.Brake);

        //Configure Drive Motor
        driveMotor.setNeutralMode(NeutralMode.Brake);
        driveMotor.setInverted(invertDrive);

        
        // Setup Current Limiting
        angleMotor.configContinuousCurrentLimit(ANGLECONTINUOUSCURRENTLIMIT, SWERVETIMEOUT);
        angleMotor.configPeakCurrentLimit(ANGLEPEAKCURRENT, SWERVETIMEOUT);
        angleMotor.configPeakCurrentDuration(ANGLEPEAKCURRENTDURATION, SWERVETIMEOUT);
        angleMotor.enableCurrentLimit(ANGLEENABLECURRENTLIMIT);

        driveMotor.configContinuousCurrentLimit(DRIVECONTINUOUSCURRENTLIMIT, SWERVETIMEOUT);
        driveMotor.configPeakCurrentLimit(DRIVEPEAKCURRENT, SWERVETIMEOUT);
        driveMotor.configPeakCurrentDuration(DRIVEPEAKCURRENTDURATION, SWERVETIMEOUT);
        driveMotor.enableCurrentLimit(DRIVEENABLECURRENTLIMIT);

    }

    public TalonSRX getAngleMotor(){
        return mAngleMotor;
    }

    public void setTargetVelocity(Vector2 velocity, boolean speed, double rotation){
            this.velocity = velocity;
            targetAngle = velocity.getAngle().toDegrees();
            smartAngle = targetAngle;
            if(speed){
            targetSpeed = velocity.length;
            }
            else{
            targetSpeed = 0;
            }
    }

    public Vector2 getModulePosition(){
        return modulePosition;
    }

    public TalonSRX getDriveMotor() {
        return mDriveMotor;
    }

    public double getDrivePos(){
        return mDriveMotor.getSelectedSensorPosition(SLOTIDX);
    }
    
    public double getTargetAngle() {
        return lastTargetAngle;
    }

    public void setDriveInverted(boolean inverted) {
        driveInverted = inverted;
    }

    public void setTargetAngle(double targetAngle) {
        targetAngle = modulate360(targetAngle);
        targetAngle += mZeroOffset;
        double currentAngle = mAngleMotor.getSelectedSensorPosition(0) * (360.0/1024.0);
        double currentAngleMod = modulate360(currentAngle);
        if (currentAngleMod < 0) currentAngleMod += 360;

        double delta = currentAngleMod - targetAngle;
        if (delta > 180) {
            targetAngle += 360;
        } else if (delta < -180) {
            targetAngle -= 360;
        }
    
        /*delta = currentAngleMod - targetAngle;
        if (delta > 90 || delta < -90) {
            if(delta > 90){
                targetAngle += 180;
            }
            else if(delta < -90){
                targetAngle -= 180;
            }            
            setDriveInverted(false);;

        } else { 
            setDriveInverted(true);
        }*/


        targetAngle += currentAngle - currentAngleMod;
        lastTargetAngle = targetAngle;
        
        targetAngle = toCounts(targetAngle);
        mAngleMotor.set(ControlMode.Position, targetAngle);
    }

    public void setTargetSpeed(double speed) {
        if (driveInverted) {speed = -speed;}
        mDriveMotor.set(ControlMode.PercentOutput, speed);
    } 




    /**
     * @return Current Angle of Module
     */
    public  double getPos(){
        double relativePosition = modulate360(toDegrees(mAngleMotor.getSelectedSensorPosition()));
        if ( relativePosition < 0){ relativePosition += 360;}
        return relativePosition;
    }

}
