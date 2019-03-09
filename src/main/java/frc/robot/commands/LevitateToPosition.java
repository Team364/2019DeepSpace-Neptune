package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

public class LevitateToPosition extends Command {

    private double position;
    public LevitateToPosition(double position) {
        setTimeout(5);
        this.position = position;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() { 
        System.out.println("Levitating Up");   
        Neptune.climber.levitateToPos(position);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
        
    }

    @Override
    protected void end() {
        System.out.println("Levitate Up has ended");
    }
}
