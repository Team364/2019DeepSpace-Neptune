package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TeleopDriveCommand extends Command {

    static enum DriveStates {
        STATE_NOT_MOVING,
        STATE_DIRECT_DRIVE, 
        STATE_RAMP_DOWN
    }

    public DriveStates driveState;
    private double steer;
    private double throttle;
    private double frontThrottle;
    private double backThrottle;

    /**
     * Command used for teleop control specific to the drive system
     * <p>Driver control
     */
    public TeleopDriveCommand() {
        requires(Robot.driveSystem);
        //Other commands can interrupt this command
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        driveState = DriveStates.STATE_NOT_MOVING;
    }

    @Override
    protected void end() {
        Robot.driveSystem.stop();
    }

    @Override
    protected void execute() {
        //Right Trigger
        frontThrottle = Robot.oi.controller.getRawAxis(2);
        //Left Trigger
        backThrottle = Robot.oi.controller.getRawAxis(3);
        //Left and Right direction on the left Joystick
        steer = -Robot.oi.controller.getRawAxis(0);

        // normal tank drive control
        if (driveState == DriveStates.STATE_NOT_MOVING) {
            throttle = 0;
            if ((Math.abs(frontThrottle) >= 0.25) || (Math.abs(backThrottle) >= 0.25) || (Math.abs(steer) >= 0.25)) {
                System.out.println("STATE_NOT_MOVING->STATE_DIRECT_DRIVE");
                driveState = DriveStates.STATE_DIRECT_DRIVE;
            }

        } else if (driveState == DriveStates.STATE_DIRECT_DRIVE) {
            throttle = backThrottle - frontThrottle;
            if ((Math.abs(frontThrottle) < 0.2) && (Math.abs(backThrottle) < 0.2)) {
                System.out.println("STATE_DIRECT_DRIVE->STATE_RAMP_DOWN");
                driveState = DriveStates.STATE_RAMP_DOWN;
            }

        } else if (driveState == DriveStates.STATE_RAMP_DOWN) {
            driveState = DriveStates.STATE_NOT_MOVING;

        } else {
            // This condition should never happen!
            driveState = DriveStates.STATE_NOT_MOVING;
        }

        Robot.driveSystem.triggerDrive(throttle, steer);

    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected boolean isFinished() {
        // This command will only end when interrupted during teleop mode
        return false;
    }

}
