package frc.robot.commands.teleop;

//import edu.wpi.first.wpilibj.HLUsageReporting.Null;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.commands.teleop.Subroutines.TeleopTurn180;
//import frc.robot.subsystems.VisionSystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.PIDCalc;
//import edu.wpi.first.networktables.*;

//import frc.robot.commands.teleop.*;

public class TeleopInterpretControlsCommand extends Command {

    public double leftControllerInput;
    public double rightControllerInput;
    public TeleopDriveCommand teleopDriveCommand = new TeleopDriveCommand();
    public TeleopAlignWithTape teleopAlignWithTape = new TeleopAlignWithTape();
    public TeleopTurn180 teleopTurn180 = new TeleopTurn180();

    /**
     * Command used to interpret user controls and trigger all other commands
     */
    public TeleopInterpretControlsCommand() {
        //requires(Robot.driveSystem);
    }

    @Override
    protected void initialize() {

        rightControllerInput = -Robot.oi.controller.getRawAxis(1);
        leftControllerInput = -Robot.oi.controller.getRawAxis(5);
    }

    @Override
    protected void end() {
        // This will probably never be called.
        // Robot.driveSystem.stop();
    }

    @Override
    protected void execute() {

        //if ((Math.abs(leftControllerInput) >= 0.25) || (Math.abs(rightControllerInput) >= 0.25)) {

            // Always schedule a TeleopDriveCommand, otherwise robot will not STOP
            // TODO: Make TeleopDriveCommand interruptible so that other driveSystem commands can execute
            
            System.out.println("USER ACTION: DRIVE");
            // Trigger TeleopDriveCommand
            teleopDriveCommand.start();
        //}
        if(Robot.oi.controller.getRawButton(10)) {
            System.out.println("USER ACTION: FOLLOW CUBE");
            // Trigger TeleopFollowCubeCommand
            // TODO: make follow cube command
        }
        if(Robot.oi.controller.getRawButton(1)){
            System.out.println("USER ACTION: TURN 180");
            // Trigger TeleopTurn180Command
            teleopTurn180.start();
        }
        if(Robot.oi.controller.getRawButton(2)){
            System.out.println("USER ACTION: ALIGN WITH TAPE");
            // Trigger TeleopAlignWithTapeCommand
            teleopAlignWithTape.start();
        }

        SmartDashboard.putNumber("GetLeftContr: ", leftControllerInput);
        SmartDashboard.putNumber("GetRightContr: ", rightControllerInput);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
