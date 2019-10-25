package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.subsystems.SwerveMod;


public class ResetMods extends Command {

    public ResetMods() {
        requires(Neptune.elevator);

    }

    @Override
    protected void initialize() {
        setTimeout(0.2);
    }

    @Override
    protected void execute() {
        for(SwerveMod mod : Neptune.driveTrain.getSwerveModules()){
            mod.resetMod();
        }
    }


    @Override
    protected void end() {
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }
}
