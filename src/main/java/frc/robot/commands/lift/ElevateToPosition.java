package frc.robot.commands.lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.States;

public class ElevateToPosition extends Command {

    private double wantedPosition;
    private double low;
    private double cargo;
    private double med;
    private double high;
    private double desiredHeight;

    /**
     * Heights
     * <p>1: low on rocket, scoring hatches on rocket level 1 and Cargo Ship
     * <p>2: middle on rocket
     * <p>3: high on rocket
     * <p>4: cargo height for scoring on Cargo Ship
     * @param Height
     */
    public ElevateToPosition(int Height) {
        desiredHeight = Height;  
        setTimeout(0.2);
    }

    @Override
    protected void initialize() {
        // Robot.liftSystem.pidLift.setPIDParameters(0.001, 0, 0, 0);
        if(States.objState == States.ObjectStates.HATCH_OBJ){
            low = 1000;
            med = 2000;
            high = 3000;
            cargo = 1500;
        }else if(States.objState == States.ObjectStates.CARGO_OBJ){
            low = 1500;
            med = 2500;
            high = 3500;
            cargo = 2000;
        }
        
        if(desiredHeight == 1){
            wantedPosition = low;
            }else if(desiredHeight == 2){
            wantedPosition = med;
            }else if(desiredHeight == 3){
            wantedPosition = high;
            }else if(desiredHeight == 4){
            wantedPosition = cargo;    
            }
    }

    @Override
    protected void execute() {
        // double pidLiftOutput;
        // Robot.liftSystem.setLiftPosition(wantedPosition);
        // pidLiftOutput = Robot.liftSystem.pidLift.calculateOutput(wantedPosition, Robot.liftSystem.getLiftPosition());
        // Robot.liftSystem.leftLift.set(ControlMode.PercentOutput, pidLiftOutput);

        System.out.println("The Object State is: " + States.objState);
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
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
