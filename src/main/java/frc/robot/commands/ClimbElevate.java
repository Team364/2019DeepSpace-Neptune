package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.defaultcommands.Periodic;

public class ClimbElevate extends Command {

    public ClimbElevate() {  
        requires(Robot.superStructure.elevatorSystem);
        setInterruptible(true);
        setTimeout(1);
    }

    @Override
    protected void initialize() {
        Periodic.manualControl = false;
 
    }

    @Override
    protected void execute() {
        Robot.superStructure.elevatorSystem.elevateTo(80000, 1500);
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
