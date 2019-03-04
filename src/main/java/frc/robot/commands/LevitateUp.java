package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

public class LevitateUp extends Command {

    public LevitateUp() {
        setTimeout(1.3);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() { 
        System.out.println("Levitating Up");   
        Neptune.climber.driveLevitator(1);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Neptune.climber.driveLevitator(0);
        System.out.println("Levitate Up has ended");
    }
}
