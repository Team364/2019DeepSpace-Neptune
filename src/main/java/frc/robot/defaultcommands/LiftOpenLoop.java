package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LiftOpenLoop extends Command {

    /**
     * Command used for teleop control specific to the lift System
     * <p>Operator controled manually
     */
    public LiftOpenLoop() {
        requires(Robot.liftSystem);
        //Other commands can interrupt this command
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double power = Robot.oi2.controller2.getRawAxis(1);
        if(power >= 0.1){
            Robot.liftSystem.openLoop(power);
        }else if(power <= 0.1){
            Robot.liftSystem.openLoop(power);
        }else{
            Robot.liftSystem.stop();;
            //Make sure to counteract gravity somehow. Maybe keep liftPosition PID?
            //Name it retainPosition or something
        }
    }

    @Override
    protected void interrupted() {
        end();
    }
    @Override
    protected void end() {
        Robot.liftSystem.stop();
    }

    @Override
    protected boolean isFinished() {
        // This command will only end when interrupted during teleop mode
        //by buttons in the Operator Interface
        return false;
    }

}
