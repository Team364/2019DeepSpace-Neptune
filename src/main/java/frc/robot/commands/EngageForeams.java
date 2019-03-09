package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

public class EngageForeams extends Command {

    private double velocity;
    public EngageForeams(double velocity) {
        setTimeout(0.5);
        this.velocity = velocity;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() { 
        System.out.println("Engaging Foreams");   
        Neptune.climber.engageForarms(velocity);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Neptune.climber.stopForarms();
        System.out.println("Foreams no longer engaged");
    }
}
