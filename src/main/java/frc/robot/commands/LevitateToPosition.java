package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

public class LevitateToPosition extends Command {

    private double position;
    public LevitateToPosition(double position) {
        setTimeout(0.1);
        this.position = position;
    }

    @Override
    protected void execute() {    
        Neptune.climber.levitateToPos(position);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();   
    }

}
