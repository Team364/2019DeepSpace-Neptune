package frc.robot.util.prefabs.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.States;
import frc.robot.util.prefabs.subsystems.ComplexTalon;
    /**
     * Used for operator only
     * @param talonBase //talon Base to run command
     * @param axis  //axis for open loop
     * @param deadband //minimum joystick value for open loop to run
     * @param bounded  //determines if open loop is bounded
     * @param upperBound //highest encoder count open loop may acheive
     * @param lowerBound //lowest encoder count open loop may acheive
     */
public class OpenLoop extends Command {

    private ComplexTalon talonBase;
    private int axis;
    private double deadband;
    private boolean bounded;
    private double upperBound;
    private double lowerBound;

    public OpenLoop(
        ComplexTalon talonBase, 
        int axis, 
        double deadband) {
        requires(talonBase);
        setInterruptible(true);
        this.talonBase = talonBase;
        this.axis = axis;
        this.deadband = deadband;
        this.bounded = talonBase.bounded;
        this.upperBound = talonBase.upperBound;
        this.lowerBound = talonBase.lowerBound;
    }

    @Override
    protected void initialize() {
        talonBase.stop();
    }

    @Override
    protected void execute() {
        if(States.loopState == States.LoopStates.OPEN_LOOP){
        double power = Robot.oi2.controller2.getRawAxis(axis);
        //TODO: Test this:
        // if(talonBase.loopState.toString() == "OPEN_LOOP");
        if(bounded){
        if((Math.abs(power) >= deadband)&&(talonBase.getPosition() >= lowerBound)&&(talonBase.getPosition() < upperBound)){
            talonBase.openLoop(power);
            talonBase.outOfBounds = false;
        }else{
            talonBase.stop();
            talonBase.outOfBounds = false;
        }
        //TODO: Add option to  go down if its too high and go up if its too low
        if((talonBase.getPosition() <= lowerBound)||(talonBase.getPosition() > upperBound)){
            System.out.println("The open Loop is out of bounds");
            talonBase.outOfBounds = true;
        }
    }else{
        if(Math.abs(power) >= deadband){
            talonBase.openLoop(power);
        }else{
            talonBase.stop();
        }
    }
    }
}

    @Override
    protected void interrupted() {
        end();
    }
    @Override
    protected void end() {
        talonBase.stop();
    }

    @Override
    protected boolean isFinished() {
        /* This command will only end when interrupted during teleop mode
        by buttons in the Operator Interface*/
        return false;
    }

}
