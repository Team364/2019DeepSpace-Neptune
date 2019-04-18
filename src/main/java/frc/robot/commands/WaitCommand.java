package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class WaitCommand extends Command {

    public WaitCommand(double seconds) {
        setTimeout(seconds);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
    
}
