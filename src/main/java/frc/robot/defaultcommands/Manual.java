package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.util.States;
import frc.robot.util.prefabs.subsystems.TalonBase;
import frc.robot.util.PIDCalc;
    /**
     * Used for operator only
     * @param talonBase //talon Base to run command
     * @param axis  //axis for open loop
     * @param deadband //minimum joystick value for open loop to run
     */
public class Manual extends Command {

    private double power;
    private double currentPosition;
    private double adjustedPosition;

    public Manual() {
        requires(Robot.superStructure.elevatorSystem);
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        currentPosition  = Robot.superStructure.lift.getPosition();
    }

    @Override
    protected void execute() {
        
        power = -Robot.oi2.controller2.getRawAxis(1)*0.5;
        if(power > 0.1){
            if(!Periodic.manualControl){
                adjustedPosition = Robot.superStructure.lift.getPosition();
                Periodic.manualControl = true;
            }
            adjustedPosition += 700;
        }else if(power < -0.1){
            adjustedPosition -= 700;
        }
        Robot.superStructure.lift.MoveToPosition(adjustedPosition);
       
    SmartDashboard.putNumber("Adjusted Position", adjustedPosition);
    }
    @Override
    protected void interrupted() {
        end();
    }
    @Override
    protected void end() {
    }

    @Override
    protected boolean isFinished() {
        /* This command will only end when interrupted during teleop mode
        by buttons in the Operator Interface*/
        return false;
    }

}
