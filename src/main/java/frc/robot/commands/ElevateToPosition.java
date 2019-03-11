package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.RobotMap;
import frc.robot.States;

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
    double scoreOnHigh;
    private double armStartConfig;
    double desiredAngle;
    double custom;
    private double lvlone;
    double lvltwo;
    private double lvlthree;
    double liftClimb;
    double armClimb;

    int camera;
    private int l1cam;
    private int l2cam;
    private int l3cam;
    private int frontCam;
    private int intakeCam;

    private int loops;

    /**
     * Heights
     * <p>0: cargo intake
     * <p>1: lvl 1 rocket, hatches on Cargo Ship
     * <p>2: lvl 2 rocket
     * <p>3: lvl 3 on rocket
     * <p>4: cargo height for scoring on Cargo Ship
     * <p>5: inside frame perimeter with game object
     * <p>6: level 3 climb for deploying forearms
     * <p>7: return to zero on lift for climbing
     * <p>8: Unknown
     * <p>9: level 2 climb for deploying forearms
     * @param Height
     */
    public ElevateToPosition(int Height) {
        desiredHeight = Height;
        requires(Neptune.elevator);
        setInterruptible(true);
        setTimeout(0.05);
    }

    @Override
    protected void initialize() {
        Neptune.manualControl = false;
        States.actionState = States.ActionStates.SCORE_ACT;
        loops = 0;
    }

    @Override
    protected void execute() {
        //Sets that are the same across cargo and hatch mode
        liftStartConfig = RobotMap.liftStartConfig;
        frontCam = RobotMap.fCam;
        intakeCam = RobotMap.l1Ccam;
        lvlone = RobotMap.armPerpindicularToGround;
        perpendicularToGround = RobotMap.armPerpindicularToGround;
        armStartConfig = RobotMap.armStartConfig;
        cargo = RobotMap.liftCargoC;
        //Object state dependent sets
        if (States.objState == States.ObjectStates.HATCH_OBJ) {
            //lift sets
            low = RobotMap.liftLowH;
            med = RobotMap.liftMedH;
            high = RobotMap.liftHighH;
            intake = low;
            //arm sets
            lvlthree = perpendicularToGround;
            //Camera sets
            l1cam = RobotMap.l1Hcam;
            l2cam = RobotMap.l2Hcam;
            l3cam = RobotMap.l3Hcam;  
        } else if (States.objState == States.ObjectStates.CARGO_OBJ) {
            //lift sets
            intake = RobotMap.liftIntake;
            low = RobotMap.liftLowC;
            med = RobotMap.liftMedC;
            high = RobotMap.liftHighC;
            //arm sets
            intakeCargo = RobotMap.armIntakeCargo;
            lvlthree = RobotMap.armScoreOnHigh;
            //Camera sets
            l1cam = RobotMap.l1Ccam;
            l2cam = RobotMap.l2Ccam;
            l3cam = RobotMap.l3Ccam;
        }

        if (desiredHeight == 0) {//Intake Position for getting Cargo
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = intake;
            wantedAngle = intakeCargo;
            camera = l1cam;
            if (loops < 1) {
                System.out.println("Intake Position " + States.objState.toString());
            }
            States.led = States.LEDstates.INTAKE_MODE;
        } else if (desiredHeight == 1) {//Level one on rocket, hatch depo, and cargoship
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = low;
            wantedAngle = lvlone;
            camera = l1cam;
            if (loops < 1) {
                System.out.println("Level 1 " + States.objState.toString());
            }
        } else if (desiredHeight == 2) {//level two on rocket
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = med;
            wantedAngle = perpendicularToGround;
            camera = l2cam;
            if (loops < 1) {
                System.out.println("Level 2 " + States.objState.toString());
            }
        } else if (desiredHeight == 3) {//level 3 on rocket
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = high;
            wantedAngle = lvlthree;
            camera = l3cam;
            if (loops < 1) {
                System.out.println("Level 3 " + States.objState.toString());
            }
        } else if (desiredHeight == 4) {//CargoShip for scoring cargo
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = cargo;
            wantedAngle = 4200;
            camera = frontCam;
            if (loops < 1) {
                System.out.println("Scoring Cargo in Cargo Ship");
            }
        } else if (desiredHeight == 5) {//Inside frame perimeter with game object
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = liftStartConfig;
            wantedAngle = 300;
            camera = frontCam;
            if(States.objState == States.ObjectStates.CARGO_OBJ){
                States.led = States.LEDstates.HAS_OBJ;
            }else if(States.objState == States.ObjectStates.HATCH_OBJ){
                States.led = States.LEDstates.PASSIVE;
            }
            if (loops < 1) {
                System.out.println("Collapsing");
            }
        } else if (desiredHeight == 6) {//level 3 Climb
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = 87000;
            wantedAngle = lvlone;
            camera = frontCam;
            if (loops < 1) {
                System.out.println("Setting up for level 3 climb");
            }
        } else if (desiredHeight == 7) {//Return to zero for climbing
            Neptune.elevator.setClimbCruiseVelocity();
            wantedPosition = liftStartConfig;
            wantedAngle = 1500;
            camera = frontCam;
            if (loops < 1) {
                System.out.println("Lift is climbing");
            }
        } else if (desiredHeight == 8) {
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = low + 5000;
            wantedAngle = lvlone;
            camera = l1cam;
        } else if(desiredHeight == 9){//level 2 Climb
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = RobotMap.liftLowC;
            wantedAngle = lvlone;
            camera = frontCam;
            if (loops < 1) {
                System.out.println("Setting up for level 2 climb");
            }
        } else if(desiredHeight == 10){
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = RobotMap.liftMedH - 3000;
            wantedAngle = 1000;
        }
        //If the position somehow is greater than what is allowed, then
        //the maximium allowed height is set again
        if (wantedPosition > 132000) {
            wantedPosition = 132000;
        }
        Neptune.elevator.elevateTo(wantedPosition, wantedAngle);
        Neptune.elevator.setCamera(camera);
        loops++;//ensures print happen once
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        loops = 0;
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }
}
