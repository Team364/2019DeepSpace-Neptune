package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.RobotMap;
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

            perpendicularToGround = RobotMap.armPerpindicularToGround;
            startConfig = RobotMap.armStartConfig;
            scoreOnHigh = perpendicularToGround;
            intakeCargo = perpendicularToGround;
        }else if(States.objState == States.ObjectStates.CARGO_OBJ){
            intakeCargo = RobotMap.armIntakeCargo;
            perpendicularToGround = RobotMap.armPerpindicularToGround;
            scoreOnHigh = RobotMap.armScoreOnHigh;
            startConfig = RobotMap.armStartConfig;
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
        Robot.superStructure.arm.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }
}
