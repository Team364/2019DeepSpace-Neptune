package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;

public class DriveOpenLoop extends Command {

    double[] real;

    public double leftPower;
    public double rightPower;

    static enum DriveStates {
        STATE_NOT_MOVING,
        STATE_DIRECT_DRIVE, 
        STATE_RAMP_DOWN
    }

    public DriveStates driveState;
    private double steer;//X axis of Left Joystick of Driver Controller
    private double throttle;//Difference of Right and Left Trigger Values
    private double frontThrottle;//Right Trigger of Driver Controller
    private double backThrottle;//Left Trigger of Driver Controller
    private double Deadband1 = 0.25;//Moves drive train 
    private double DeadBand2 = 0.2;//Stops drive train

    /**
     * Driver control
     */
    public DriveOpenLoop() {
        requires(Neptune.driveTrain);  
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        driveState = DriveStates.STATE_NOT_MOVING;
        real = null;
    }

    @Override
    protected void execute() {
        frontThrottle = Neptune.oi.controller.getRawAxis(2);//Right Trigger
        backThrottle = Neptune.oi.controller.getRawAxis(3);//Left Trigger
        steer = 0.7*Neptune.oi.controller.getRawAxis(0);//X-axis of left Joystick
        if (driveState == DriveStates.STATE_NOT_MOVING) {
            throttle = 0;
            if ((Math.abs(frontThrottle) >= Deadband1) || (Math.abs(backThrottle) >= Deadband1) || (Math.abs(steer) >= Deadband1)) {
                driveState = DriveStates.STATE_DIRECT_DRIVE;
            }
        } else if (driveState == DriveStates.STATE_DIRECT_DRIVE) {
            throttle = backThrottle - frontThrottle;
            if ((Math.abs(frontThrottle) < DeadBand2) && (Math.abs(backThrottle) < DeadBand2)) {
                driveState = DriveStates.STATE_RAMP_DOWN;
            }
        } else if (driveState == DriveStates.STATE_RAMP_DOWN) {
            driveState = DriveStates.STATE_NOT_MOVING;
        } else {
            driveState = DriveStates.STATE_NOT_MOVING;
        }
        leftPower = throttle + steer;
        rightPower = throttle - steer;
        Neptune.driveTrain.openLoop(leftPower, rightPower);
    

        SmartDashboard.putNumber("Left Power", leftPower);
        SmartDashboard.putNumber("Right Power", rightPower);
        SmartDashboard.putNumber("Left Speed", Neptune.driveTrain.getLeftCounts());
        SmartDashboard.putNumber("Right Speed", -Neptune.driveTrain.getRightCounts());
        SmartDashboard.putNumber("Gyro", Neptune.elevator.getYaw());
    }
   /* public void runDrive(){
        frontThrottle = Neptune.oi.controller.getRawAxis(2);//Right Trigger
        backThrottle = Neptune.oi.controller.getRawAxis(3);//Left Trigger
        steer = -0.7*Neptune.oi.controller.getRawAxis(0);//X-axis of left Joystick
        if (driveState == DriveStates.STATE_NOT_MOVING) {
            throttle = 0;
            if ((Math.abs(frontThrottle) >= Deadband1) || (Math.abs(backThrottle) >= Deadband1) || (Math.abs(steer) >= Deadband1)) {
                driveState = DriveStates.STATE_DIRECT_DRIVE;
            }
        } else if (driveState == DriveStates.STATE_DIRECT_DRIVE) {
            throttle = backThrottle - frontThrottle;
            if ((Math.abs(frontThrottle) < DeadBand2) && (Math.abs(backThrottle) < DeadBand2)) {
                driveState = DriveStates.STATE_RAMP_DOWN;
            }
        } else if (driveState == DriveStates.STATE_RAMP_DOWN) {
            driveState = DriveStates.STATE_NOT_MOVING;
        } else {
            driveState = DriveStates.STATE_NOT_MOVING;
        }
        leftPower = throttle + steer;
        rightPower = throttle - steer;
        real[0] = leftPower;
        real[1] = rightPower;
    }*/

    public double[] getReal(){
        return real;
    }

    @Override
    protected void end() {
        Neptune.driveTrain.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
