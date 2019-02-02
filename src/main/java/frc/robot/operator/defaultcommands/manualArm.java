package frc.robot.operator.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class manualArm extends Command {

    /**
     * Command used for teleop control specific to the arn System
     * <p>Operator controled manually
     */
    public manualArm() {
        requires(Robot.armSystem);
        //Other commands can interrupt this command
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.armSystem.moveArm(Robot.oi2.rightStick);
    }

    @Override
    protected void interrupted() {
        end();
    }
    @Override
    protected void end() {
        //Robot.armSystem.stop();
    }

    @Override
    protected boolean isFinished() {
        // This command will only end when interrupted during teleop mode
        //by buttons in the Operator Interface
        return false;
    }

}
