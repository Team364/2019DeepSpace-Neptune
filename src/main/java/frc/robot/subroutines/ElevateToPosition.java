package frc.robot.subroutines;

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
    private double scoreOnHigh;
    private double armStartConfig;
    private double desiredAngle;
    private double custom;
    private double lvlone;
    private double lvltwo;
    private double lvlthree;
    private double liftClimb;
    private double armClimb;

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
            lvlthree = 2800;
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

        }
        liftClimb = 80000;
        armClimb = 1500;

        if (desiredHeight == 0) {
            wantedPosition = intake;
            wantedAngle = intakeCargo;
        } else if (desiredHeight == 1) {
            wantedPosition = low;
            wantedAngle = lvlone;
        } else if (desiredHeight == 2) {
            wantedPosition = med;
            wantedAngle = perpendicularToGround;
        } else if (desiredHeight == 3) {
            wantedPosition = high;
            wantedAngle = lvlthree;
        } else if (desiredHeight == 4) {
            wantedPosition = cargo;
            wantedAngle = perpendicularToGround;
        } else if (desiredHeight == 5) {
            wantedPosition = liftStartConfig;
            wantedAngle = armStartConfig;
        } else if (desiredHeight == 6) {
            wantedPosition = liftClimb;
            wantedAngle = armClimb;
        }

        Neptune.elevator.elevateTo(wantedPosition, wantedAngle);
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
