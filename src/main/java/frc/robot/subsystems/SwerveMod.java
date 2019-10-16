package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;
import frc.robot.misc.math.Vector2;

import static frc.robot.Conversions.*;
import static frc.robot.RobotMap.*;

import java.util.Vector;


public class SwerveMod{
    private double lastTargetAngle = 0;
    public final int moduleNumber;

    public final double mZeroOffset;

    private final TalonSRX mAngleMotor;
    private final TalonSRX mDriveMotor;
    private Vector2 modulePosition;
    private boolean driveInverted = false;
    
    public double targetAngle;
    public double targetSpeed;
    public double smartAngle;
    public Vector2 velocity;
    public double vectorOffset;
    public double currentAngle;

    public SwerveMod(int moduleNumber, Vector2 modulePosition, TalonSRX angleMotor, TalonSRX driveMotor, double zeroOffset, double vectorOffset) {
        this.moduleNumber = moduleNumber;
        this.modulePosition = modulePosition;
        this.vectorOffset = vectorOffset;
        mAngleMotor = angleMotor;
        mDriveMotor = driveMotor;
        mZeroOffset = zeroOffset;
        targetAngle = mZeroOffset;
        targetSpeed = 0;
        currentAngle = 0;

        angleMotor.configFactoryDefault();
        angleMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, SLOTIDX, SWERVETIMEOUT);
        //angleMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 1, SWERVETIMEOUT);
        //angleMotor.setSelectedSensorPosition(0, SLOTIDX, SWERVETIMEOUT);
        angleMotor.selectProfileSlot(SLOTIDX, SWERVETIMEOUT);
        angleMotor.config_kP(SLOTIDX, ANGLEP);
        angleMotor.config_kI(SLOTIDX, ANGLEI);
        angleMotor.config_kD(SLOTIDX, ANGLED);
        angleMotor.setNeutralMode(NeutralMode.Brake);
        angleMotor.set(ControlMode.Position, 0);
        angleMotor.configMotionCruiseVelocity(ANGLEVELOCITY, SWERVETIMEOUT);
        angleMotor.configMotionAcceleration(ANGLEACCELERATION, SWERVETIMEOUT);

        angleMotor.configNominalOutputForward(ANGLENOMINALFORWARD, SWERVETIMEOUT);
        angleMotor.configNominalOutputReverse(ANGLENOMINALREVERSE, SWERVETIMEOUT);
        angleMotor.configPeakOutputForward(ANGLEPEAKFORWARD, SWERVETIMEOUT);
        angleMotor.configPeakOutputReverse(ANGLEPEAKREVERSE, SWERVETIMEOUT);

        driveMotor.setNeutralMode(NeutralMode.Brake);

        // Set amperage limits
        angleMotor.configContinuousCurrentLimit(ANGLECONTINUOUSCURRENTLIMIT, SWERVETIMEOUT);
        angleMotor.configPeakCurrentLimit(ANGLEPEAKCURRENT, SWERVETIMEOUT);
        angleMotor.configPeakCurrentDuration(ANGLEPEAKCURRENTDURATION, SWERVETIMEOUT);
        angleMotor.enableCurrentLimit(ANGLEENABLECURRENTLIMIT);

        driveMotor.configContinuousCurrentLimit(DRIVECONTINUOUSCURRENTLIMIT, SWERVETIMEOUT);
        driveMotor.configPeakCurrentLimit(DRIVEPEAKCURRENT, SWERVETIMEOUT);
        driveMotor.configPeakCurrentDuration(DRIVEPEAKCURRENTDURATION, SWERVETIMEOUT);
        driveMotor.enableCurrentLimit(DRIVEENABLECURRENTLIMIT);

        driveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, SLOTIDX, SWERVETIMEOUT);
        driveMotor.setSelectedSensorPosition(0, SLOTIDX, SWERVETIMEOUT);
        driveMotor.selectProfileSlot(SLOTIDX, SWERVETIMEOUT);
        driveMotor.config_kP(SLOTIDX, ANGLEP);
        driveMotor.config_kI(SLOTIDX, ANGLEI);
        driveMotor.config_kD(SLOTIDX, ANGLED);
    }

    public TalonSRX getAngleMotor(){
        return mAngleMotor;
    }
    public double getCurrentAngle(){
        double angle = currentAngle;
        return angle;
    }


    public void setTargetVelocity(Vector2 velocity, boolean speedOff, double rotation){
            this.velocity = velocity;
            if(moduleNumber == 1){
                targetAngle = -(velocity.getAngle().toDegrees()) + (Math.abs(rotationDeadband(rotation)) * (vectorOffset - 90)) ;
            }
            else{
                targetAngle = velocity.getAngle().toDegrees() + (Math.abs(rotationDeadband(rotation)) * (vectorOffset - 90)) - (Math.abs(rotationDeadband(rotation)) * 90 * (vectorOffset-90)) + (rotationDeadband(rotation) * 180);

            }
            smartAngle = targetAngle;
            if(!speedOff){
            targetSpeed = velocity.length;
            }
            else{
            targetSpeed = 0;
            }
    }
    public double getVelocityX(){
        return velocity.x;
    }
    public double getVelocityY(){
        return velocity.y;
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
    public void setTargetAngleVal(double targetAngle){
        this.targetAngle = targetAngle;
    }
    public void setTargetSpeedVal(double targetSpeed){
        this.targetSpeed = targetSpeed;
    }
    public void setTargetAngle(double targetAngle) {
        targetAngle = modulate360(targetAngle);
        double currentAngle = toDegrees(getPos());
        //double currentAngleMod = currentAngle;
        double currentAngleMod = modulate360(currentAngle);
        if (currentAngleMod < 0) currentAngleMod += 360;
        double delta = currentAngleMod - targetAngle;
        if (delta > 180) {
            targetAngle += 360;
        } else if (delta < -180) {
            targetAngle -= 360;
        }
        delta = currentAngleMod - targetAngle;
        if (delta > 90 || delta < -90) {
            if (delta > 90)
                targetAngle += 180;
            else if (delta < -90)
                targetAngle -= 180;
            mDriveMotor.setInverted(false);
        } else {
            mDriveMotor.setInverted(true);
        }

        targetAngle += currentAngle - currentAngleMod;
        double currentError = getRawError();
        lastTargetAngle = targetAngle;
        targetAngle = toCounts(targetAngle);
        //smartAngle = targetAngle;
            mAngleMotor.set(ControlMode.Position, targetAngle);
            mAngleMotor.set(ControlMode.Position, targetAngle);
    }

    public void setTargetSpeed(double speed) {
        if (driveInverted) speed = -speed;
        mDriveMotor.set(ControlMode.PercentOutput, speed);
    }

    public double getRawError(){
        return mAngleMotor.getClosedLoopError(0);
    }
    
    public double getAdjustedError(){
        return toDegrees(getRawError());
    }

    public  double getPos(){
        return mAngleMotor.getSelectedSensorPosition(SLOTIDX);
    }

    /**
     * @return Ticks of Position
     */
    public double getTicks(){
        return mAngleMotor.getSensorCollection().getPulseWidthPosition() & 0xFFF;
    }
    public double getOffset(){
        return mZeroOffset;
    }
    public void zero(){
        mAngleMotor.setSelectedSensorPosition(0, SLOTIDX, SWERVETIMEOUT);
    }

    public void setSensorPhase(boolean invert){
        mAngleMotor.setSensorPhase(invert);
    }

}
