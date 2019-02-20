package frc.robot.util.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.util.subsystems.TalonBase;
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
    private double lastPosition;

    public OpenLoop(
        TalonBase talonBase, 
        int axis, 
        double deadband) {
        requires(talonBase);
        setInterruptible(true);
        this.talonBase = talonBase;
        this.axis = axis;
        this.deadband = deadband;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        power = Robot.oi2.controller2.getRawAxis(axis);

        if(Math.abs(power) >= deadband){
            talonBase.openLoop(power);
            lastPosition = talonBase.getPosition();
        }else{
            talonBase.stop();
        }
    
    //SmartDashBoard
    String pow = talonBase.getTalonName() + " Open Loop Power: ";
    String axis = talonBase.getTalonName() + " Open Loop Axis: ";
    SmartDashboard.putNumber(pow, this.power);
    SmartDashboard.putNumber(axis, this.axis);
    SmartDashboard.putNumber(talonBase.getTalonName() + " keeping pos: ", lastPosition);
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
