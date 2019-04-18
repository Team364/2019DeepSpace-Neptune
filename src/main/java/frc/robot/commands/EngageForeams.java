package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

public class EngageForeams extends Command {

    private double power;
    public EngageForeams(double velocity) {
        setTimeout(0.5);
        this.power = velocity;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {  
        Neptune.climber.engageForarms(power);
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
