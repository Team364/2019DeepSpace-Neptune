package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TeleopLiftCommand extends Command {

    /**
     * Command used for teleop control specific to the lift System
     * <p>Operator controled manually
     */
    public TeleopLiftCommand() {
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
            Robot.liftSystem.manualLiftControl(power);
        }else if(power <= 0.1){
            Robot.liftSystem.manualLiftControl(power);
        }else{
            Robot.liftSystem.manualLiftControl(0);
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
