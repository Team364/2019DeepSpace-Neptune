package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveOpenLoop extends Command {

    static enum DriveStates {
        STATE_NOT_MOVING,
        STATE_DIRECT_DRIVE, 
        STATE_RAMP_DOWN
    }

    public DriveStates driveState;
    //X axis of Left Joystick of Driver Controller
    private double steer;
    //Difference of Right and Left Trigger Values
    private double throttle;
    //Right Trigger of Driver Controller
    private double frontThrottle;
    //Left Trigger of Driver Controller
    private double backThrottle;
    private double leftPower;
    private double rightPower;
    //Deadbands
    /**Moves drive train */
    private double Deadband1 = 0.25;
    /**Stops drive train */
    private double DeadBand2 = 0.2;

    /**
     * Command used for teleop control specific to the drive system
     * <p>Driver control
     */
    public DriveOpenLoop() {
        requires(Robot.superStructure.driveTrain);
        //Other commands can interrupt this command
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        //Robot is waiting for driver input
        driveState = DriveStates.STATE_NOT_MOVING;
    }

    @Override
    protected void execute() {
        //Right Trigger
        frontThrottle = Robot.oi.controller.getRawAxis(2);
        //Left Trigger
        backThrottle = Robot.oi.controller.getRawAxis(3);
        //X-axis of left Joystick
        steer = -Robot.oi.controller.getRawAxis(0);
        //normal Drive Control
        //If the robot isn't moving and then either Trigger is activated and pressed beyond 0.25, the robot will
        //change state into Direct Drive
        if (driveState == DriveStates.STATE_NOT_MOVING) {
            throttle = 0;
            if ((Math.abs(frontThrottle) >= Deadband1) || (Math.abs(backThrottle) >= Deadband1) || (Math.abs(steer) >= Deadband1)) {
                System.out.println("STATE_NOT_MOVING->STATE_DIRECT_DRIVE");
                driveState = DriveStates.STATE_DIRECT_DRIVE;
            }
        //Once Robot is in direct drive, if the triggers values are below 0.2, the robot will enter a ramp down state
        } else if (driveState == DriveStates.STATE_DIRECT_DRIVE) {
            throttle = backThrottle - frontThrottle;
            if ((Math.abs(frontThrottle) < DeadBand2) && (Math.abs(backThrottle) < DeadBand2)) {
                System.out.println("STATE_DIRECT_DRIVE->STATE_RAMP_DOWN");
                driveState = DriveStates.STATE_RAMP_DOWN;
            }
        } else if (driveState == DriveStates.STATE_RAMP_DOWN) {
            driveState = DriveStates.STATE_NOT_MOVING;
        } else {
            // This condition should never happen!
            driveState = DriveStates.STATE_NOT_MOVING;
        }

        //This is where the driveSystem is actually asked to run motors
        // Robot.driveSystem.openLoop(throttle, steer);
        leftPower = throttle + steer;
        rightPower = throttle - steer;
        Robot.superStructure.driveOpenLoop(rightPower, leftPower);
    

    }

    @Override
    protected void end() {
        Robot.superStructure.driveTrain.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected boolean isFinished() {
        // This command will only end when interrupted during teleop mode
        //by buttons in the Operator Interface
        return false;
    }

}
