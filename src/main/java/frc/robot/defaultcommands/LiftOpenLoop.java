package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.States;

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
        if(States.loopState == States.LoopStates.OPEN_LOOP){
        double power = Robot.oi2.controller2.getRawAxis(1);
        double counts = Robot.liftSystem.getLiftPosition();
        if((Math.abs(power) >= 0.1)&&(counts >= Robot.liftSystem.lowerBound)&&(counts < Robot.liftSystem.upperBound)){
            Robot.liftSystem.openLoop(power);
        }else{
            System.out.println("lift motors should have stopped here");
            Robot.liftSystem.stop();
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
        Robot.liftSystem.stop();
    }

    @Override
    protected boolean isFinished() {
        // This command will only end when interrupted during teleop mode
        //by buttons in the Operator Interface
        return false;
    }

}
