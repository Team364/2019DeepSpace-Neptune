package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class RotateToPosition extends Command {

    private double wantedPosition;
    private double intakeCargo;
    private double perpendicularToGround;
    private double scoreOnHigh;
    private double startConfig;
    private double desiredPosition;

    /**
     * Position Set:
     * <p>1: intakeCargo Position for intaking Cargo
     * <p>2: perpendicular to Ground Position for scoring Hatches 
     * <p>3: scoreOnHigh for scoring on Level 3 of rocket
     * <p>4: startConfig used for retract
     * @param Position
     */
    public RotateToPosition(int Position) {
        desiredPosition = Position;  
        setTimeout(0.2);
    }

    @Override
    protected void initialize() {
        // Robot.liftSystem.pidLift.setPIDParameters(0.001, 0, 0, 0);
        if(Robot.objState == Robot.ObjectStates.HATCH_OBJ){
            //TODO:
            //measure out where these values are before this is ran on
            //the acutual robot
            intakeCargo = 100;
            perpendicularToGround = 200;
            scoreOnHigh = 300;
            startConfig = 0;
        }else if(Robot.objState == Robot.ObjectStates.CARGO_OBJ){
            intakeCargo = 150;
            perpendicularToGround = 250;
            scoreOnHigh = 350;
            startConfig = 0;
        }
        
        if(desiredPosition == 1){
            wantedPosition = intakeCargo;
            }else if(desiredPosition == 2){
            wantedPosition = perpendicularToGround;
            }else if(desiredPosition == 3){
            wantedPosition = scoreOnHigh;
            }else if(desiredPosition == 4){
            wantedPosition = startConfig;
            }
    }

    @Override
    protected void execute() {
        //TODO: Change this to arm stuff
        // double pidLiftOutput;
        // Robot.liftSystem.setLiftPosition(wantedPosition);
        // pidLiftOutput = Robot.liftSystem.pidLift.calculateOutput(wantedPosition, Robot.liftSystem.getLiftPosition());
        // Robot.liftSystem.leftLift.set(ControlMode.PercentOutput, pidLiftOutput);

        System.out.println("The Object State is: " + Robot.objState);
        System.out.println("The Position that has been selected is: " + desiredPosition);
        System.out.println("The encoder count being fed to the execute method is: " + wantedPosition);
    }

    @Override
    protected boolean isFinished() {
        // return Robot.liftSystem.reachedPosition(wantedPosition) || isTimedOut();
         return isTimedOut();
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
