package frc.robot.subsystems;

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
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotController;
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

    public AnalogInput angleEncoder;

    public double anglePercent;

    protected PIDController angleController;

    public SwerveMod(int moduleNumber, Vector2 modulePosition, TalonSRX angleMotor, AnalogInput angleEncoder, TalonSRX driveMotor, boolean invertDrive, boolean invertSensorPhase, int zeroOffset) {
        this.moduleNumber = moduleNumber;
        this.modulePosition = modulePosition;
        mAngleMotor = angleMotor;
        mDriveMotor = driveMotor;
        mZeroOffset = zeroOffset;
        targetAngle = 0;
        targetSpeed = 0;
        currentAngle = 0;
        this.invertSensorPhase = invertSensorPhase;
        this.angleEncoder = angleEncoder;

        


        angleMotor.selectProfileSlot(SLOTIDX, SWERVETIMEOUT);
        angleMotor.config_kP(SLOTIDX, ANGLEP, SWERVETIMEOUT);
        angleMotor.config_kI(SLOTIDX, ANGLEI, SWERVETIMEOUT);
        angleMotor.config_kD(SLOTIDX, ANGLED, SWERVETIMEOUT);
        angleMotor.setNeutralMode(NeutralMode.Brake);

        angleMotor.setInverted(invertSensorPhase);
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

        angleController = new PIDController(0.01, 0.00000, 0.000, new PIDSource() {
        
            public void setPIDSourceType(PIDSourceType pidSource){
            }
     
            public PIDSourceType getPIDSourceType(){
                return PIDSourceType.kDisplacement;
            }

            public double pidGet() {
                return getPos();
            }

        }, output -> {
            anglePercent = output;
        });
        angleController.enable();
        angleController.setInputRange(0, 360);
        angleController.setOutputRange(-1, 1);
        angleController.setContinuous();
    }

    public TalonSRX getAngleMotor(){
        return mAngleMotor;
    }

    public void setTargetVelocity(Vector2 velocity, boolean speed, double rotation){
            this.velocity = velocity;
            targetAngle = -velocity.getAngle().toDegrees();
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

        final double currentAngle = getPos();

        double delta = targetAngle - currentAngle;
        if (delta >= 180) {
            targetAngle -= 360;
        } else if (delta < -180) {
            targetAngle += 360;
        }

        delta = targetAngle - currentAngle;
        if (delta > 90 || delta < -90) {
            targetAngle += 180;

            targetSpeed *= -1.0;
        }

        targetAngle %= 360;
        if (targetAngle < 0.0) {
            targetAngle += 360;
        }

        angleController.setSetpoint(targetAngle);
        lastTargetAngle = anglePercent;
        mAngleMotor.set(ControlMode.PercentOutput, anglePercent);
    }

    public void setTargetSpeed(double speed) {
        //if (driveInverted) {speed = -speed;}
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

    /*public  double getPos(){
        double relativePos = mAngleMotor.getSelectedSensorPosition(SLOTIDX);
        return relativePos;
    }    */

    public double getPos() {
        double angle = (1.0 - angleEncoder.getVoltage() / RobotController.getVoltage5V()) * 360;
        angle %= 360;
        if (angle < 0.0) {
            angle += 360;
        }

        return angle;
    }

    public void setSensorPhase(boolean invert){
        mAngleMotor.setSensorPhase(invert);
    }

}
