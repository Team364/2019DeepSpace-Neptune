package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.States;

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
     * WORKS
     */
    public ElevateToPosition(int Height) {
        desiredHeight = Height;  
        setTimeout(0.2);
        requires(Robot.superStructure.lift);
    }

    @Override
    protected void initialize() {
        /*One must keep in mind that a Position of 4096 is only a full rotation of the axle the encoder
        corresponds to. This means that these values may be quite large in practice.
        Writing an equation which converts the inches on the lift to raw sensor units would be beyond useful */
        // Robot.liftSystem.pidLift.setPIDParameters(0.001, 0, 0, 0);
        if(States.objState == States.ObjectStates.HATCH_OBJ){
            low = 10000;
            med = 20000;
            high = 30000;
            cargo = 15000;
        }else if(States.objState == States.ObjectStates.CARGO_OBJ){
            low = 15000;
            med = 25000;
            high = 35000;
            cargo = 20000;
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
        System.out.println("The Object State is: " + States.objState);
        System.out.println("The Height that has been selected is: " + desiredHeight);
        System.out.println("The encoder count being fed to the execute method is: " + wantedPosition);
        Robot.superStructure.lift.MoveToPosition(wantedPosition);
        States.loopState = States.LoopStates.CLOSED_LOOP;
    }

    @Override
    protected boolean isFinished() {
        return Robot.superStructure.lift.reachedPosition();
    }

    @Override
    protected void end() {
        Robot.superStructure.lift.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
