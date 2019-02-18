package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.util.States;
import frc.robot.util.prefabs.subsystems.TalonBase;
import frc.robot.util.PIDCalc;
import frc.robot.RobotMap;
    /**
     * Used for operator only
     * @param talonBase //talon Base to run command
     * @param axis  //axis for open loop
     * @param deadband //minimum joystick value for open loop to run
     */
public class ArmOpenLoop extends Command {

    private TalonBase talonBase;
    private int axis;
    private double power;
    private double deadband;
    private double lastPosition;
    private PIDCalc keepPosition;

    public ArmOpenLoop(
        TalonBase talonBase, 
        int axis, 
        double deadband) {
        requires(talonBase);
        setInterruptible(true);
        this.talonBase = talonBase;
        this.axis = axis;
        this.deadband = deadband;
        keepPosition = new PIDCalc(-0.0012, 0, 0, 0, "armKeepPosition");
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        double armWeight = 10;
        double armDistance = 18;
        double motorStallTorque = 300;
        double gearRatio = 3;
        double angle = Math.cos(Math.abs(RobotMap.armPerpindicularToGround) - Math.abs(Robot.superStructure.arm.getPosition()) / 
        (Math.abs(RobotMap.armPerpindicularToGround) / 90));
        double FeedForward = armWeight * armDistance / motorStallTorque * gearRatio * angle;
        keepPosition.setPIDParameters(-0.0012, 0, 0, FeedForward);
    if(States.loopState == States.LoopStates.OPEN_LOOP){
        power = Robot.oi2.controller2.getRawAxis(axis);
        if(Math.abs(power) > deadband){
            talonBase.openLoop(power);
            lastPosition = talonBase.getPosition();
        }else{
            double output = keepPosition.calculateOutput(lastPosition, talonBase.getPosition());
            talonBase.openLoop(output);
        }
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
