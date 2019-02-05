package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class manualArm extends Command {

    public double rightStick;
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
        rightStick = Robot.oi2.controller2.getRawAxis(5)
        if(rightStick >= 0.5){
            Robot.armSystem.moveArm(1);
        }else if(rightStick <= -0.5){
            Robot.armSystem.moveArm(-1);
        }else{
            Robot.armSystem.moveArm(0);
             //CounterAct Gravity Somehow
        }
       
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
