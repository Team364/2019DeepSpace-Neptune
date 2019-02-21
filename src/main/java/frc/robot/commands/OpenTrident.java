package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

public class OpenTrident extends Command {

    public OpenTrident() {
        requires(Neptune.trident);
        setTimeout(0.04);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Neptune.trident.open();    
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Neptune.trident.noInput();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }
}
