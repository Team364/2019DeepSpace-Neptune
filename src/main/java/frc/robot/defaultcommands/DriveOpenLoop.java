package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveOpenLoop extends Command {

    static enum DriveStates {
        STATE_NOT_MOVING,
        STATE_DIRECT_DRIVE,
        STATE_TURN, 
        STATE_RAMP_DOWN
    }

    public DriveStates driveState;
    private double steer;//X axis of Left Joystick of Driver Controller
    private double throttle;//Difference of Right and Left Trigger Values
    private double frontThrottle;//Right Trigger of Driver Controller
    private double backThrottle;//Left Trigger of Driver Controller
    private double leftPower;
    private double rightPower;
    private double Deadband1 = 0.25;//Moves drive train 
    private double DeadBand2 = 0.2;//Stops drive train
    private double DeadBand3 = 0.15;

    /**
     * Driver control
     */
    public DriveOpenLoop() {
        requires(Neptune.driveTrain);  
        setInterruptible(true);//Other commands can interrupt this command
    }

    @Override
    protected void initialize() {
        driveState = DriveStates.STATE_NOT_MOVING;//Robot is waiting for driver input
    }

    @Override
    protected void execute() {
        frontThrottle = Neptune.oi.controller.getRawAxis(2);//Right Trigger
        backThrottle = Neptune.oi.controller.getRawAxis(3);//Left Trigger
        steer = -Neptune.oi.controller.getRawAxis(0);//X-axis of left Joystick
        /*normal Drive Control
        If the robot isn't moving and then either Trigger is activated and pressed beyond 0.25, the robot will
        change state into Direct Drive*/
        if (driveState == DriveStates.STATE_NOT_MOVING) {
            throttle = 0;
            if ((Math.abs(frontThrottle) >= Deadband1) || (Math.abs(backThrottle) >= Deadband1)){
                //System.out.println("STATE_NOT_MOVING->STATE_DIRECT_DRIVE");
                driveState = DriveStates.STATE_DIRECT_DRIVE;
            }else if((Math.abs(steer) >= Deadband1)){
                driveState = DriveStates.STATE_TURN;
            }
        //Once Robot is in direct drive, if the triggers values are below 0.2, the robot will enter a ramp down state
        } else if (driveState == DriveStates.STATE_DIRECT_DRIVE) {
            throttle = backThrottle - frontThrottle;
            steer = steer * 0.7;
            if ((Math.abs(frontThrottle) < DeadBand2) && (Math.abs(backThrottle) < DeadBand2)) {
                //System.out.println("STATE_DIRECT_DRIVE->STATE_RAMP_DOWN");
                driveState = DriveStates.STATE_RAMP_DOWN;
            }
        
        } else if(driveState == DriveStates.STATE_TURN){
            throttle = 0;
            if((Math.abs(steer) <= DeadBand3)){
                driveState = DriveStates.STATE_RAMP_DOWN;
            }

        } else if (driveState == DriveStates.STATE_RAMP_DOWN) {
            // TODO: Implement slow down code
            driveState = DriveStates.STATE_NOT_MOVING;
        } else {
            driveState = DriveStates.STATE_NOT_MOVING;//This condition should never happen!
        }
        //This is where the driveSystem is actually asked to run motors
        leftPower = throttle + steer;
        rightPower = throttle - steer;
        Neptune.driveTrain.openLoop(rightPower, leftPower);
    
        SmartDashboard.putString("Drive State", driveState.toString());
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
        // This command will only end when interrupted during teleop mode
        //by buttons in the Operator Interface
        return false;
    }

}
