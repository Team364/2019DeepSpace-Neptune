package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.misc.math.Rotation2;
import frc.robot.misc.math.Vector2;

public class FinalSequence extends Command {

    public FinalSequence() {
        setTimeout(0.5);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {  
        Vector2 translation = new Vector2(.3, 0); 
        translation = translation.rotateBy(Rotation2.fromDegrees(Neptune.elevator.getGyro()).inverse());
        Neptune.driveTrain.holonomicDrive(translation, 0, true);
        Neptune.climber.driveWheelsToWin();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Neptune.climber.stopForarms();
    }
}
