package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.RobotMap;

public class FinalSequence extends Command {

    double angle;
    double level;
    /**
     * <p>This drives the climb wheels and drivetrain forward
     * <p>while keeping the levitator at the climbPosition
     * <p>and moving the arm to start config
     */
    public FinalSequence(int level) {
        requires(Neptune.driveTrain);
        setTimeout(1.6);
        this.level = level;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Neptune.driveTrain.openLoop(0.6, 0.6);
        Neptune.climber.driveWheelsToWin();  
        Neptune.elevator.setArmAngle(100);
        System.out.println("Final Sequence of climb is executing");
        if(level == 3){
            Neptune.climber.levitateToPos(RobotMap.lvl3Climb);
        }else if(level == 2){
            Neptune.climber.levitateToPos(RobotMap.lvl2Climb);
        }else if(level == 1){
            Neptune.climber.levitateToPos(RobotMap.intermediateClimb);
        }
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Neptune.driveTrain.stop();
        Neptune.climber.stop();
        Neptune.climber.levitateToPos(100);
        System.out.println("Final climb command has ended");
    }
}