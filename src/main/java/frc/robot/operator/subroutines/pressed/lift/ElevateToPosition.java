package frc.robot.operator.subroutines.pressed.lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.Robot.ObjectStates;


public class ElevateToPosition extends Command {

    private double wantedPosition;
    private double low;
    private double med;
    private double high;
    //Only used for testing
    private double desiredHeight;

    public ElevateToPosition(int Height) {
        requires(Robot.driveSystem);
        if(Robot.objState == Robot.ObjectStates.HATCH_OBJ){
            low = 1000;
            med = 2000;
            high = 3000;
        }else if(Robot.objState == Robot.ObjectStates.CARGO_OBJ){
            low = 1500;
            med = 2500;
            high = 3000;
        }
        if(Height == 1){
        wantedPosition = low;
        }else if(Height == 2){
        wantedPosition = med;
        }else if(Height == 3){
        wantedPosition = high;
        }
        //Used only for testing
        desiredHeight = Height;
        
        setTimeout(3);
    }

    @Override
    protected void initialize() {
        // Robot.liftSystem.pidLift.setPIDParameters(0.001, 0, 0, 0);
    }

    @Override
    protected void execute() {
        // double pidLiftOutput;
        // Robot.liftSystem.setLiftPosition(wantedPosition);
        // pidLiftOutput = Robot.liftSystem.pidLift.calculateOutput(wantedPosition, Robot.liftSystem.getLiftPosition());
        // Robot.liftSystem.leftLift.set(ControlMode.PercentOutput, pidLiftOutput);
        System.out.println("The Object State is: " + Robot.objState);
        System.out.println("The Height that has been selected is: " + desiredHeight);
        System.out.println("The encoder count being fed to the execute method is: " + wantedPosition);
    }

    @Override
    protected boolean isFinished() {
        // return Robot.liftSystem.reachedPosition(wantedPosition) || isTimedOut();
         return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.liftSystem.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
