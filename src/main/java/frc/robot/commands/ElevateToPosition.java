package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.defaultcommands.Periodic;
import frc.robot.util.States;

public class ElevateToPosition extends Command {

    private double wantedPosition;
    private double low;
    private double cargo;
    private double med;
    private double high;
    private double intake;
    private double liftStartConfig;
    private double desiredHeight;
    
    private double wantedAngle;
    private double intakeCargo;
    private double perpendicularToGround;
    private double scoreOnHigh;
    private double armStartConfig;
    private double desiredAngle;
    private double custom;
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
        requires(Robot.superStructure.elevatorSystem);
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        Periodic.manualControl = false;
        /*One must keep in mind that a Position of 4096 is only a full rotation of the axle the encoder
        corresponds to. This means that these values may be quite large in practice.
        Writing an equation which converts the inches on the lift to raw sensor units would be beyond useful */
 
    }

    @Override
    protected void execute() {
        liftStartConfig = RobotMap.liftStartConfig;
 
        if(States.objState == States.ObjectStates.HATCH_OBJ){
            low = RobotMap.liftLowH;
            med = RobotMap.liftMedH;
            high = RobotMap.liftHighH;
            cargo = low;
            intake = low;

            perpendicularToGround = RobotMap.armPerpindicularToGround;
            armStartConfig = perpendicularToGround;
            scoreOnHigh = perpendicularToGround;
            intakeCargo = perpendicularToGround;
            custom = 3500;
        }else if(States.objState == States.ObjectStates.CARGO_OBJ){
            intake = RobotMap.liftIntake;
            low = RobotMap.liftLowC;
            med = RobotMap.liftMedC;
            high = RobotMap.liftHighC;
            cargo = RobotMap.liftCargoC;

            intakeCargo = RobotMap.armIntakeCargo;
            perpendicularToGround = RobotMap.armPerpindicularToGround;
            scoreOnHigh = perpendicularToGround;
            armStartConfig = perpendicularToGround;
            custom = 5000;
        }
        
        if(desiredHeight == 0){
            wantedPosition = intake;
            wantedAngle = intakeCargo;
            }else if(desiredHeight == 1){
            wantedPosition = low;
            wantedAngle = custom;
            }else if(desiredHeight == 2){
            wantedPosition = med;
            wantedAngle = perpendicularToGround;
            }else if(desiredHeight == 3){
            wantedPosition = high;
            wantedAngle = perpendicularToGround;
            }else if(desiredHeight == 4){
            wantedPosition = cargo; 
            wantedAngle = perpendicularToGround;   
            }else if(desiredHeight == 5){
            wantedPosition = liftStartConfig;
            wantedAngle = armStartConfig;
            }
         
        Robot.superStructure.elevatorSystem.elevateTo(wantedPosition, wantedAngle);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }
}
