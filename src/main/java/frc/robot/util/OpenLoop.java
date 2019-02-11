package frc.robot.util;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.States;
import frc.robot.subsystems.SuperStructure;
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

    private TalonBase talonBase;
    private int axis;
    private double deadband;
    private boolean bounded;
    private double upperBound;
    private double lowerBound;

    public OpenLoop(
        TalonBase talonBase, 
        int axis, 
        double deadband, 
        boolean bounded,
        double upperBound,
        double lowerBound) {
        requires(talonBase);
        setInterruptible(true);
        this.talonBase = talonBase;
        this.axis = axis;
        this.deadband = deadband;
        this.bounded = bounded;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    @Override
    protected void initialize() {
        talonBase.stop();
    }

    @Override
    protected void execute() {
        if(States.loopState == States.LoopStates.OPEN_LOOP){
        double power = Robot.oi2.controller2.getRawAxis(axis);
        if(bounded){
        if((Math.abs(power) >= deadband)&&(talonBase.getPosition() >= lowerBound)&&(talonBase.getPosition() < upperBound)){
            talonBase.openLoop(power);
            talonBase.outOfBounds = false;
        }else{
            talonBase.stop();
            talonBase.outOfBounds = false;
        }
        if((talonBase.getPosition() <= lowerBound)||(talonBase.getPosition() > upperBound)){
            System.out.println("The open Loop is out of bounds");
            talonBase.outOfBounds = true;
        }
    }else{
        if(Math.abs(power) >= deadband){
            talonBase.openLoop(power);
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
