package frc.robot.subsystems;

import static frc.robot.Conversions.*;
import static frc.robot.RobotMap.ANGLECONTINUOUSCURRENTLIMIT;
import static frc.robot.RobotMap.ANGLED;
import static frc.robot.RobotMap.ANGLEENABLECURRENTLIMIT;
import static frc.robot.RobotMap.ANGLEI;
import static frc.robot.RobotMap.ANGLEP;
import static frc.robot.RobotMap.ANGLEPEAKCURRENT;
import static frc.robot.RobotMap.ANGLEPEAKCURRENTDURATION;
import static frc.robot.RobotMap.DRIVECONTINUOUSCURRENTLIMIT;
import static frc.robot.RobotMap.DRIVEENABLECURRENTLIMIT;
import static frc.robot.RobotMap.DRIVEPEAKCURRENT;
import static frc.robot.RobotMap.DRIVEPEAKCURRENTDURATION;
import static frc.robot.RobotMap.SLOTIDX;
import static frc.robot.RobotMap.SWERVETIMEOUT;

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
    //private int absolutePosition;
    private boolean invertSensorPhase;

    public SwerveMod(int moduleNumber, Vector2 modulePosition, TalonSRX angleMotor, TalonSRX driveMotor, boolean invertDrive, boolean invertSensorPhase, int zeroOffset) {
        this.moduleNumber = moduleNumber;
        this.modulePosition = modulePosition;
        mAngleMotor = angleMotor;
        mDriveMotor = driveMotor;
        mZeroOffset = zeroOffset;
        targetAngle = 0;
        targetSpeed = 0;
        currentAngle = 0;
        this.invertSensorPhase = invertSensorPhase;


        // Configure Angle Motor
        angleMotor.configFactoryDefault();
        angleMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, SLOTIDX, SWERVETIMEOUT);
        angleMotor.configFeedbackNotContinuous(false, SWERVETIMEOUT); //make false if using relative, make true if using absolute
        angleMotor.selectProfileSlot(SLOTIDX, SWERVETIMEOUT);
        angleMotor.setSensorPhase(invertSensorPhase);
        angleMotor.config_kP(SLOTIDX, ANGLEP, SWERVETIMEOUT);
        angleMotor.config_kI(SLOTIDX, ANGLEI, SWERVETIMEOUT);
        angleMotor.config_kD(SLOTIDX, ANGLED, SWERVETIMEOUT);
        angleMotor.setNeutralMode(NeutralMode.Brake);

        
        driveMotor.setNeutralMode(NeutralMode.Brake);

        
        // Setup Current Limiting
        angleMotor.configContinuousCurrentLimit(ANGLECONTINUOUSCURRENTLIMIT, SWERVETIMEOUT);
        angleMotor.configPeakCurrentLimit(ANGLEPEAKCURRENT, SWERVETIMEOUT);
        angleMotor.configPeakCurrentDuration(ANGLEPEAKCURRENTDURATION, SWERVETIMEOUT);
        angleMotor.enableCurrentLimit(ANGLEENABLECURRENTLIMIT);

        driveMotor.configContinuousCurrentLimit(DRIVECONTINUOUSCURRENTLIMIT, SWERVETIMEOUT);
        driveMotor.configPeakCurrentLimit(DRIVEPEAKCURRENT, SWERVETIMEOUT);
        driveMotor.configPeakCurrentDuration(DRIVEPEAKCURRENTDURATION, SWERVETIMEOUT);
        driveMotor.enableCurrentLimit(DRIVEENABLECURRENTLIMIT);        
        setDriveInverted(invertDrive);

    }

    public TalonSRX getAngleMotor(){
        return mAngleMotor;
    }

    public void setTargetVelocity(Vector2 velocity, boolean speed, double rotation){
            this.velocity = velocity;
            if(moduleNumber == 1){
                targetAngle = -(velocity.getAngle().toDegrees());
            }
            else{
                targetAngle = velocity.getAngle().toDegrees();
            }
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
        double currentAngle = toDegrees(getPos());
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
        lastTargetAngle = targetAngle;
    
        targetAngle = toCounts(targetAngle);
        mAngleMotor.set(ControlMode.Position, targetAngle);
    }

    public void setTargetSpeed(double speed) {
        if (driveInverted) {speed = -speed;}
        mDriveMotor.set(ControlMode.PercentOutput, speed);
    } 



    /**
     * @return Ticks of Position
     */
    public int getTicks(){
        return mAngleMotor.getSensorCollection().getPulseWidthPosition() & 0xFFF;
    }

    public void resetMod(){
        //absolutePosition = getTicks();
        //if(invertSensorPhase){absolutePosition *= -1;}
        zero();
    }

    public void zero(){
        int pulseWidth = mAngleMotor.getSensorCollection().getPulseWidthPosition();
        if(invertSensorPhase){pulseWidth *= -1;}
        int moduleOffset;
        moduleOffset = mZeroOffset;
		moduleOffset &= 0xFFF;
		pulseWidth += moduleOffset;
        pulseWidth = pulseWidth & 0xFFF;
        mAngleMotor.setSelectedSensorPosition(pulseWidth, SLOTIDX, SWERVETIMEOUT);
        //mAngleMotor.setSelectedSensorPosition(modulate4096(absolutePosition + mZeroOffset), SLOTIDX, SWERVETIMEOUT);
    }

    public  double getPos(){
        double relativePos = mAngleMotor.getSelectedSensorPosition(SLOTIDX);
        return relativePos;
    }    

    public void setSensorPhase(boolean invert){
        mAngleMotor.setSensorPhase(invert);
    }

}
