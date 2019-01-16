package frc.robot.commands.teleop;

//import edu.wpi.first.wpilibj.HLUsageReporting.Null;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.VisionSystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.PIDCalc;
import edu.wpi.first.networktables.*;

public class TeleopDriveCommand extends Command {

    public double leftControllerInput;
    public double rightControllerInput;
    public static Command RampDown;
    public boolean rampDownSequence;
    public boolean forward;
    public double leftVelocity;
    public double rightVelocity;

    static enum DriveStates {
        STATE_NOT_MOVING, STATE_DIRECT_DRIVE, STATE_RAMP_DOWN //, STATE_FOLLOW_CUBE, STATE_TURN_180, STATE_LINE_UP_TAPE
    }

    public DriveStates driveState;
    public double tankLeft;
    public double tankRight;
    public boolean CancelRamp;
    // public NetworkTableEntry centerX;
    // public NetworkTableEntry area;
    public double centerX;
    public double area;
    // public double[] x;
    // public double[] a;
    public double x;
    public double a;
    public PIDCalc pidXvalue;
    public double pidOutputXvalue;
    public PIDCalc pidAvalue;
    public double pidOutputAvalue;

    /**
     * Command used for teleop control specific to the drive system
     */
    public TeleopDriveCommand() {
        requires(Robot.driveSystem);
        // RampDown = new RampDown();
        CancelRamp = false;
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        driveState = DriveStates.STATE_NOT_MOVING;
        rampDownSequence = false;
        pidXvalue = new PIDCalc(0.001, 0.001, 0.0, 0.0, "follow");
        pidAvalue = new PIDCalc(0.001, 0.0, 0.0, 0.0, "area");
        // NetworkTableInstance inst = NetworkTableInstance.getDefault();
        // NetworkTable table = inst.getTable("GRIP/contours");
        // centerX = table.getEntry("centerX");
        // area = table.getEntry("area");
    }

    @Override
    protected void end() {
        // This will probably never be called.
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

            /*
             * } else if (driveState == DriveStates.STATE_TURN_180) { Robot.Turn180.start();
             * if(Robot.driveSystem.reachedHeadingL(175)){ driveState =
             * DriveStates.STATE_NOT_MOVING; }
             * 
             * 
             * } else if (driveState == DriveStates.STATE_LINE_UP_TAPE) {
             * if((!Robot.oi.controller.getRawButton(2))||(!Robot.visionSystem.
             * visionTargetSeen)) {
             * System.out.println("STATE_LINE_UP_TAPE->STATE_NOT_MOVING"); driveState =
             * DriveStates.STATE_NOT_MOVING; } if(Robot.visionSystem.visionTargetSeen){
             * pidOutputXvalue = pidXvalue.calculateOutput(120, Robot.visionSystem.centerX);
             * pidOutputAvalue = pidAvalue.calculateOutput(800,
             * Robot.visionSystem.targetArea); //tankLeft = pidOutputXvalue +
             * pidOutputAvalue; //tankRight = -pidOutputXvalue + pidOutputAvalue; tankLeft =
             * pidOutputXvalue; tankRight = -pidOutputXvalue; }
             */

        } else {
            // This condition should never happen!
            driveState = DriveStates.STATE_NOT_MOVING;
        }

        Robot.driveSystem.tankDrive(tankLeft, tankRight);

        if (Robot.oi.shiftHigh.get()) {
            Robot.driveSystem.shiftHigh();
        } else if (Robot.oi.shiftLow.get()) {
            Robot.driveSystem.shiftLow();
        } else {
            Robot.driveSystem.noShiftInput();
        }
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
