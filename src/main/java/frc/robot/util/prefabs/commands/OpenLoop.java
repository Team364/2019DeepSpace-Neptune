package frc.robot.util.prefabs.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.util.States;
import frc.robot.util.prefabs.subsystems.TalonBase;
    /**
     * Used for operator only
     * @param talonBase //talon Base to run command
     * @param axis  //axis for open loop
     * @param deadband //minimum joystick value for open loop to run
     */
public class OpenLoop extends Command {

    private TalonBase talonBase;
    private int axis;
    private double power;
    private double deadband;
    private boolean bounded;
    private double upperBound;
    private double lowerBound;
    private double position;

    public OpenLoop(
        TalonBase talonBase, 
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
        this.position = talonBase.getPosition();
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    if(States.loopState == States.LoopStates.OPEN_LOOP){
        power = Robot.oi2.controller2.getRawAxis(axis);
        if(bounded){
        if((Math.abs(power) >= deadband)&&(position >= lowerBound)&&(position < upperBound)){
            talonBase.openLoop(power);
            talonBase.isOutsideBounds(false);
        }else if((position <= lowerBound)||(position > upperBound)){
            System.out.println("The " + talonBase.getTalonName() + " open Loop is out of bounds");
            talonBase.isOutsideBounds(true);
        }else{
            talonBase.stop();
            talonBase.isOutsideBounds(false);
        }
      
    }else{
        if(Math.abs(power) >= deadband){
            talonBase.openLoop(power);
        }else{
            talonBase.stop();
        }
    }
    }
    //SmartDashBoard
    String pow = talonBase.getTalonName() + " Open Loop Power: ";
    String axis = talonBase.getTalonName() + " Open Loop Axis: ";
    String pos = talonBase.getTalonName() + " Open Loop Talon Position: ";//SHOULD BE EXACTLY THE SAME AS POSITION THAT TALON GIVES
    SmartDashboard.putNumber(pow, this.power);
    SmartDashboard.putNumber(axis, this.axis);
    SmartDashboard.putNumber(pos, this.position);
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
