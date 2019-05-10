package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.States;


public class DriveClosedLoop extends Command {
    
    //parameter switched to this variable for transfer to motion magic
    private double target;

    public DriveClosedLoop(double Target) {
        requires(Neptune.driveTrain);
        target = Target;
        //was in elevatetoposition.java, thought i might include it
        setInterruptible(true);
        setTimeout(0.05);

    }

    @Override
    protected void initialize() {
        Neptune.manualControl = false;
    }

    @Override
    protected void execute() {
        Neptune.driveTrain.closedLoop(target);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }


    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
