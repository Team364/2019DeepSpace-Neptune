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

    double camera;
    private double l1cam;
    private double l2cam;
    private double l3cam;
    private double frontCam;
    private double intakeCam;

    /**
     * Heights
     * <p>
     * 1: low on rocket, scoring hatches on rocket level 1 and Cargo Ship
     * <p>
     * 2: middle on rocket
     * <p>
     * 3: high on rocket
     * <p>
     * 4: cargo height for scoring on Cargo Ship
     * 
     * @param Height
     */
    public ElevateToPosition(int Height) {
        desiredHeight = Height;
        requires(Neptune.elevator);
        setInterruptible(true);
        setTimeout(0.2);
    }

    @Override
    protected void initialize() {
        Neptune.manualControl = false;
    }

    @Override
    protected void execute() {
        liftStartConfig = RobotMap.liftStartConfig;
        frontCam = RobotMap.fCam;
        if (States.objState == States.ObjectStates.HATCH_OBJ) {
            low = RobotMap.liftLowH;
            med = RobotMap.liftMedH;
            high = RobotMap.liftHighH;
            cargo = low;
            intake = low;

            perpendicularToGround = RobotMap.armPerpindicularToGround;
            armStartConfig = RobotMap.armStartConfig;
            scoreOnHigh = perpendicularToGround;
            intakeCargo = 3300;

            lvlone = perpendicularToGround;
            lvlthree = perpendicularToGround;

            l1cam = RobotMap.l1Hcam;
            l2cam = RobotMap.l2Hcam;
            l3cam = RobotMap.l3Hcam;
            intakeCam = RobotMap.l1Ccam;
        } else if (States.objState == States.ObjectStates.CARGO_OBJ) {
            intake = RobotMap.liftIntake;
            low = RobotMap.liftLowC;
            med = RobotMap.liftMedC;
            high = RobotMap.liftHighC;
            cargo = RobotMap.liftCargoC;

            intakeCargo = RobotMap.armIntakeCargo;
            perpendicularToGround = RobotMap.armPerpindicularToGround;
            scoreOnHigh = perpendicularToGround;
            armStartConfig = RobotMap.armStartConfig;
            custom = 5000;
            lvlone = 3300;
            lvlthree = 2100;

            l1cam = RobotMap.l1Ccam;
            l2cam = RobotMap.l2Ccam;
            l3cam = RobotMap.l3Ccam;
            intakeCam = RobotMap.iCcam;

        }

        if (desiredHeight == 0) {
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = intake;
            wantedAngle = intakeCargo;
            camera = frontCam;
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
            camera = intakeCam;
        } else if (desiredHeight == 5) {
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = liftStartConfig;
            wantedAngle = 300;
            camera = frontCam;
        } else if (desiredHeight == 6) {
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = RobotMap.liftMedC - 5000;
            wantedAngle = lvlone;
            camera = frontCam;
        } else if (desiredHeight == 7) {
            Neptune.elevator.setClimbCruiseVelocity();
            wantedPosition = liftStartConfig;
            wantedAngle = 1500;
            camera = frontCam;
        } else if (desiredHeight == 8){
            Neptune.elevator.setPlayCruiseVelocity();
            wantedPosition = low + 5000;
            wantedAngle = lvlone;
            camera = l1cam;
        }

        Neptune.elevator.elevateTo(wantedPosition, wantedAngle);
        Neptune.elevator.setCamera(camera);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
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
