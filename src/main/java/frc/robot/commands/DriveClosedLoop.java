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
        setInterruptible(false);
        setTimeout(0.05);
        Neptune.driveTrain.resetEncoders();
    }

    @Override
    protected void initialize() {
        target *= 2609;
    }

    @Override
    protected void execute() {

        //2609 ticks = 1 foot

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
