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
    private boolean finish;

    private double wantedAngle;
    private double intakeCargo;
    private double perpendicularToGround;
    double scoreOnHigh;
    double armStartConfig;
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
    int intakeCam;

    public ElevateToPosition(int Height) {
        desiredHeight = Height;
        requires(Neptune.elevator);
        setInterruptible(true);
        //setTimeout(0.05);
    }

    @Override
    protected void initialize() {
        States.actionState = States.ActionStates.SCORE_ACT;
        finish = false;
    }

    @Override
    protected void execute() {
        liftStartConfig = RobotMap.liftStartConfig;
        frontCam = RobotMap.fCam;
        if (States.objState == States.ObjectStates.HATCH_OBJ) {
            //Lift Hatch
            low = RobotMap.liftLowH;
            med = RobotMap.liftMedH;
            high = RobotMap.liftHighH;
            cargo = low;
            intake = low - 1000;
            //Arm Hatch
            perpendicularToGround = RobotMap.armPerpindicularToGround;
            armStartConfig = RobotMap.armStartConfig;
            scoreOnHigh = perpendicularToGround;
            intakeCargo = perpendicularToGround;
            lvlone = perpendicularToGround;
            lvlthree = perpendicularToGround;
            //Camera Hatch
            l1cam = RobotMap.l1Hcam;
            l2cam = RobotMap.l2Hcam;
            l3cam = RobotMap.l3Hcam;
            intakeCam = RobotMap.l1Ccam;
        } else if (States.objState == States.ObjectStates.CARGO_OBJ) {
            //Lift Cargo
            intake = RobotMap.liftIntake;
            low = RobotMap.liftLowC;
            med = RobotMap.liftMedC;
            high = RobotMap.liftHighC;
            cargo = RobotMap.liftCargoC;
            //Arm Cargo
            intakeCargo = RobotMap.armIntakeCargo;
            perpendicularToGround = RobotMap.armPerpindicularToGround;
            scoreOnHigh = perpendicularToGround;
            armStartConfig = RobotMap.armStartConfig;
            custom = 5000;
            lvlone = 3300;
            lvlthree = 2200;
            //Camera Cargo
            l1cam = RobotMap.l1Ccam;
            l2cam = RobotMap.l2Ccam;
            l3cam = RobotMap.l3Ccam;
            intakeCam = RobotMap.l1Ccam;
        }

        if (desiredHeight == 0) {
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = intake;
            wantedAngle = intakeCargo;
            camera = l1cam;
            States.led = States.LEDstates.INTAKE_MODE;
        } else if (desiredHeight == 1) {
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = low;
            wantedAngle = lvlone;
            camera = l1cam;
        } else if (desiredHeight == 2) {
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = med;
            wantedAngle = perpendicularToGround;
            camera = l2cam;
        } else if (desiredHeight == 3) {
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = high;
            wantedAngle = lvlthree;
            camera = l3cam;
        } else if (desiredHeight == 4) {
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = cargo;
            wantedAngle = 4200;
            camera = frontCam;
        } else if (desiredHeight == 5) {
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = liftStartConfig;
            wantedAngle = 300;
            camera = frontCam;
            if(States.objState == States.ObjectStates.CARGO_OBJ){
                States.led = States.LEDstates.HAS_OBJ;
            }else if(States.objState == States.ObjectStates.HATCH_OBJ){
                States.led = States.LEDstates.PASSIVE;
            }
        } else if (desiredHeight == 6) {
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = 91000;
            wantedAngle = lvlone;
            camera = frontCam;
        } else if (desiredHeight == 7) {
            Neptune.elevator.setClimbCruiseVelocity();
            wantedPosition = 100;
            wantedAngle = 1500;
            camera = frontCam;
        } else if (desiredHeight == 8) {//lift after intaking
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = low + 9000;
            wantedAngle = lvlone;
            camera = l1cam;
        } else if(desiredHeight == 9){
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = RobotMap.liftLowC;
            wantedAngle = lvlone;
            camera = frontCam;
        } else if(desiredHeight == 10){//Not used it seems
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = RobotMap.liftMedH - 3000;
            wantedAngle = 1000;
        } else if(desiredHeight == 11){
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = liftStartConfig;
            wantedAngle = 10;
            camera = frontCam;
        }
        if (wantedPosition > 132000) {
            wantedPosition = 132000;
        }
        Neptune.elevator.elevateTo(wantedPosition, wantedAngle);
        Neptune.elevator.setCamera(camera);
        finish = true;
    }

    @Override
    protected boolean isFinished() {
        return finish;
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
