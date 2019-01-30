package frc.robot.commands.teleop.buttonsubroutines.lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Robot.ObjectStates;


public class ElevateToPosition extends Command {

    private double wantedPosition;
    private double low;
    private double med;
    private double high;

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
        
        setTimeout(3);
    }

    @Override
    protected void initialize() {
        Robot.liftSystem.pidLift.setPIDParameters(0.001, 0, 0, 0);
    }

    @Override
    protected void execute() {
        Robot.liftSystem.setLiftPosition(wantedPosition);
    }

    @Override
    protected boolean isFinished() {
        return Robot.liftSystem.reachedPosition(wantedPosition) || isTimedOut();
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
