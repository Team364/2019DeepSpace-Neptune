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
public class ArmDefault extends Command {

    private TalonBase talonBase;
    private int axis;
    private double power;
    private double deadband;
    private double lastPosition;
    private PIDCalc keepPosition;
    private double angle;
    private double encoderError;
    private double FeedForward;

    public ArmDefault(
        TalonBase talonBase, 
        int axis, 
        double deadband) {
        requires(talonBase);
        setInterruptible(true);
        this.talonBase = talonBase;
        this.axis = axis;
        this.deadband = deadband;
        keepPosition = new PIDCalc(0.0012, 0, 0, 0, "armKeepPosition");//0.0012
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        // double armWeight = 10;
        // double armDistance = 18 / 12;//Inches to feet conversion
        // double motorStallTorque = 0.71 * 0.7375;//Newton Meters to Foot Pounds Conversion
        // double gearRatio = 466.67;
        // if(Math.abs(Robot.superStructure.arm.getPosition()) < Math.abs(RobotMap.armPerpindicularToGround)){
       
        // }else if(Math.abs(Robot.superStructure.arm.getPosition()) >= Math.abs(RobotMap.armPerpindicularToGround)){
        //     angle = (Math.abs(Robot.superStructure.arm.getPosition())  / (Math.abs(RobotMap.armPerpindicularToGround) / 90));
        //   }
  
    
        
        encoderError = RobotMap.armPerpindicularToGround - Robot.superStructure.arm.getPosition();
        double conversion = RobotMap.armPerpindicularToGround / 90;
          angle = encoderError / conversion;
          FeedForward = 0.1 * Math.cos(angle);//0.0613848223
        //   if(Robot.superStructure.arm.getPosition() > RobotMap.armPerpindicularToGround){
        //     FeedForward -= 1;
        // }
        keepPosition.setPIDParameters(-0.0012, 0, 0, FeedForward);
    if(States.loopState == States.LoopStates.OPEN_LOOP){
        power = -Robot.oi2.controller2.getRawAxis(axis);
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
    SmartDashboard.putNumber("Encoder Error; ", encoderError);
    SmartDashboard.putNumber("Conversion: ", conversion);
    SmartDashboard.putNumber("Arm Angle: ", angle);
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
