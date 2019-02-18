package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.util.States;
import frc.robot.util.prefabs.subsystems.TalonBase;
import frc.robot.util.PIDCalc;
    /**
     * Used for operator only
     * @param talonBase //talon Base to run command
     * @param axis  //axis for open loop
     * @param deadband //minimum joystick value for open loop to run
     */
public class LiftOpenLoop extends Command {

    private TalonBase talonBase;
    private int axis;
    private double power;
    private double deadband;
    private double lastPosition;
    private PIDCalc keepPosition;

    public LiftOpenLoop(
        TalonBase talonBase, 
        int axis, 
        double deadband) {
        requires(talonBase);
        setInterruptible(true);
        this.talonBase = talonBase;
        this.axis = axis;
        this.deadband = deadband;
        keepPosition = new PIDCalc(0, 0, 0, 0.13, "liftKeepPosition");
    }

    @Override
    protected void initialize() {
        
    }

    @Override
    protected void execute() {
    if(States.loopState == States.LoopStates.OPEN_LOOP){
        power = -Robot.oi2.controller2.getRawAxis(axis)*0.5;

        if(!Robot.superStructure.limitArray[2] &&(power > deadband)){
            talonBase.openLoop(power);
            lastPosition = talonBase.getPosition();
        }else if(!Robot.superStructure.limitArray[3] &&(power < -deadband)){
            talonBase.openLoop(power);
            lastPosition = talonBase.getPosition();
        }else{
           double output = keepPosition.calculateOutput(lastPosition, talonBase.getPosition());
           talonBase.openLoop(output);
        }
       
    }
    //SmartDashBoard
    // String pow = talonBase.getTalonName() + " Open Loop Power: ";
    // String axis = talonBase.getTalonName() + " Open Loop Axis: ";
    // SmartDashboard.putNumber(pow, this.power);
    // SmartDashboard.putNumber(axis, this.axis);
    // SmartDashboard.putNumber(talonBase.getTalonName() + " keeping pos: ", lastPosition);
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
