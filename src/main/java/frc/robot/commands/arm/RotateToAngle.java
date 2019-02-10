package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.States;
import frc.robot.defaultcommands.Periodic;


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
        requires(Robot.armSystem);
    }

    @Override
    protected void initialize() {
        loops = 0;
        // Robot.liftSystem.pidLift.setPIDParameters(0.001, 0, 0, 0);
        if(States.objState == States.ObjectStates.HATCH_OBJ){
            //TODO:
            //measure out where these values are before this is ran on
            //the acutual robot
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
        //TODO: Change this to arm stuff
        // double pidLiftOutput;
        // Robot.liftSystem.setLiftPosition(wantedAngle);
        // pidLiftOutput = Robot.liftSystem.pidLift.calculateOutput(wantedAngle, Robot.liftSystem.getLiftPosition());
        // Robot.liftSystem.leftLift.set(ControlMode.PercentOutput, pidLiftOutput);
        ++loops;
        Robot.armSystem.setArmAngle(wantedAngle);
        System.out.println("The Object State is: " + States.objState);
        System.out.println("The Angle that has been selected is: " + desiredAngle);
        System.out.println("The encoder count being fed to the execute method is: " + wantedAngle);
    }

    @Override
    protected boolean isFinished() {
        //TODO: replicate in lift
        return Robot.armSystem.reachedPosition() && (loops > 20);
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
