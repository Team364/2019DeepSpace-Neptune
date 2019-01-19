package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TeleopDriveCommand extends Command {

    public double leftControllerInput;
    public double rightControllerInput;

    static enum DriveStates {
        STATE_NOT_MOVING,
        STATE_DIRECT_DRIVE, 
        STATE_RAMP_DOWN
    }

    public DriveStates driveState;
    public double tankLeft;
    public double tankRight;

    /**
     * Command used for teleop control specific to the drive system
     */
    public TeleopDriveCommand() {
        requires(Robot.driveSystem);
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

        rightControllerInput = -Robot.oi.controller.getRawAxis(1);
        leftControllerInput = -Robot.oi.controller.getRawAxis(5);

        // normal tank drive control
        if (driveState == DriveStates.STATE_NOT_MOVING) {
            tankLeft = 0;
            tankRight = 0;
            if ((Math.abs(leftControllerInput) >= 0.25) || (Math.abs(rightControllerInput) >= 0.25)) {
                System.out.println("STATE_NOT_MOVING->STATE_DIRECT_DRIVE");
                driveState = DriveStates.STATE_DIRECT_DRIVE;
            }

        } else if (driveState == DriveStates.STATE_DIRECT_DRIVE) {
            tankLeft = leftControllerInput;
            tankRight = rightControllerInput;
            if ((Math.abs(leftControllerInput) < 0.2) && (Math.abs(rightControllerInput) < 0.2)) {
                System.out.println("STATE_DIRECT_DRIVE->STATE_RAMP_DOWN");
                driveState = DriveStates.STATE_RAMP_DOWN;
            }

        } else if (driveState == DriveStates.STATE_RAMP_DOWN) {
            driveState = DriveStates.STATE_NOT_MOVING;

        } else {
            // This condition should never happen!
            driveState = DriveStates.STATE_NOT_MOVING;
        }

        Robot.driveSystem.tankDrive(tankLeft, tankRight);

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
