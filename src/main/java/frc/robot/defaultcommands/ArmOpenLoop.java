package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.States;

public class ArmOpenLoop extends Command {

    public double rightStick;
    /**
     * Command used for teleop control specific to the arn System
     * <p>Operator controled manually
     */
    public ArmOpenLoop() {
        requires(Robot.armSystem);
        //Other commands can interrupt this command
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        if(States.loopState == States.LoopStates.OPEN_LOOP){
        double power = Robot.oi2.controller2.getRawAxis(5);
        if((Math.abs(power) >= 0.1)){
            Robot.armSystem.openLoop(power);
            Robot.superStructure.armOutofBounds = false;
        }else{
            System.out.println("arm motors should have stopped here");
            Robot.armSystem.stop();
            //Make sure to counteract gravity somehow. Maybe keep liftPosition PID?
            //Name it retainPosition or something
        }
    }
       
    }

    @Override
    protected void interrupted() {
        end();
    }
    @Override
    protected void end() {
        Robot.armSystem.stop();
    }

    @Override
    protected boolean isFinished() {
        // This command will only end when interrupted during teleop mode
        //by buttons in the Operator Interface
        return false;
    }

}
