package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

public class CloseTrident extends Command {

    public CloseTrident() {
        requires(Neptune.trident);
        setTimeout(0.04);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Neptune.trident.close();    
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
