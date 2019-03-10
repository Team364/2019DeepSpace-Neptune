package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;

public class ElevatorManual extends Command {

    private double liftPower;
    double armPower;
    private double adjustedLiftPosition;
    double adjustedArmAngle;

    public ElevatorManual() {
        requires(Neptune.elevator);
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        
        liftPower = -Neptune.oi2.buttoBoxo.getRawAxis(1);
        if(liftPower > 0.1){
            if(!Neptune.manualControl){
                adjustedLiftPosition = Neptune.elevator.getLiftPosition();
                Neptune.manualControl = true;
            }
            adjustedLiftPosition += 700;
        }else if(liftPower < -0.1){
            adjustedLiftPosition -= 700;
        }
        if(adjustedLiftPosition > 132000){
            adjustedLiftPosition = 132000;
        }else if(adjustedLiftPosition < 0){
            adjustedLiftPosition = 0;
        }
        Neptune.elevator.setLiftPosition(adjustedLiftPosition);

       
    SmartDashboard.putNumber("Adjusted Position", adjustedLiftPosition);
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
