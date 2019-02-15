package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.States;

public class RotateToAngle extends Command {

    private double wantedAngle;
    private double intakeCargo;
    private double perpendicularToGround;
    private double scoreOnHigh;
    private double startConfig;
    private double desiredAngle;
    private int loops;

    /**
     * Angle Set:
     * <p>1: intakeCargo Angle for intaking Cargo
     * <p>2: perpendicular to Ground Angle for scoring Hatches 
     * <p>3: scoreOnHigh for scoring on Level 3 of rocket
     * <p>4: startConfig used for retract
     * @param Angle
     */
    public RotateToAngle(int Angle) {
        desiredAngle = Angle;  
        requires(Robot.superStructure.arm);
    }

    @Override
    protected void initialize() {
        loops = 0;
        if(States.objState == States.ObjectStates.HATCH_OBJ){
            intakeCargo = 1137 /*100 degrees */;
            perpendicularToGround = 2275/*200 degrees*/;
            scoreOnHigh = 3413 /*300 degrees*/;
            startConfig = 0 /*0 degrees */;
        }else if(States.objState == States.ObjectStates.CARGO_OBJ){
            intakeCargo = 1706 /*150 degrees*/;
            perpendicularToGround = 2844 /*250 degrees*/;
            scoreOnHigh = 3981 /*350 degrees*/;
            startConfig = 0;
        }
        
        if(desiredAngle == 1){
            wantedAngle = intakeCargo;
            }else if(desiredAngle == 2){
            wantedAngle = perpendicularToGround;
            }else if(desiredAngle == 3){
            wantedAngle = scoreOnHigh;
            }else if(desiredAngle == 4){
            wantedAngle = startConfig;
            }
    }

    @Override
    protected void execute() {
        ++loops;
        Robot.superStructure.arm.MoveToPosition(wantedAngle);
    }

    @Override
    protected boolean isFinished() {
        return Robot.superStructure.arm.reachedPosition() && (loops > 20);
    }

    @Override
    protected void end() {
        //Make sure to stop the arm here
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
