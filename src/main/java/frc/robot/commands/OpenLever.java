package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

public class OpenLever extends Command {

    public OpenLever() {
        requires(Neptune.trident);
        setTimeout(0.04);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Neptune.trident.openLever();    
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Neptune.trident.noInputLever();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }
}
