package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.States;

public class ElevateToPosition extends Command {

    private double wantedPosition;
    private double low;
    private double cargo;
    private double med;
    private double high;
    private double intake;
    private double startConfig;
    private double desiredHeight;
    private int loops;
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
        requires(Robot.superStructure.lift);
    }

    @Override
    protected void initialize() {
        /*One must keep in mind that a Position of 4096 is only a full rotation of the axle the encoder
        corresponds to. This means that these values may be quite large in practice.
        Writing an equation which converts the inches on the lift to raw sensor units would be beyond useful */
        startConfig = RobotMap.liftStartConfig;
        if(States.objState == States.ObjectStates.HATCH_OBJ){
            low = RobotMap.liftLowH;
            med = RobotMap.liftMedH;
            high = RobotMap.liftHighH;
            cargo = low;
            intake = low;
        }else if(States.objState == States.ObjectStates.CARGO_OBJ){
            intake = RobotMap.liftIntake;
            low = RobotMap.liftLowC;
            med = RobotMap.liftMedC;
            high = RobotMap.liftHighC;
            cargo = RobotMap.liftCargoC;
        }
        
        if(desiredHeight == 0){
            wantedPosition = intake;
            }else if(desiredHeight == 1){
            wantedPosition = low;
            }else if(desiredHeight == 2){
            wantedPosition = med;
            }else if(desiredHeight == 3){
            wantedPosition = high;
            }else if(desiredHeight == 4){
            wantedPosition = cargo;    
            }else if(desiredHeight == 5){
            wantedPosition = startConfig;
            }
    }

    @Override
    protected void execute() {
        ++loops;
        Robot.superStructure.lift.MoveToPosition(wantedPosition);
        States.loopState = States.LoopStates.CLOSED_LOOP;
    }

    @Override
    protected boolean isFinished() {
        return Robot.superStructure.lift.reachedPosition()&& (loops > 20);
    }

    @Override
    protected void end() {
        Robot.superStructure.lift.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }
}
