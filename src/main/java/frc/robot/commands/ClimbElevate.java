package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ClimbElevate extends Command {

    public ClimbElevate() {  
        requires(Robot.elevator);
        setInterruptible(true);
        setTimeout(1);
    }

    @Override
    protected void initialize() {
        Robot.manualControl = false;
 
    }

    @Override
    protected void execute() {
        Robot.elevator.elevateTo(80000, 1500);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }
}
