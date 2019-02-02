package frc.robot.operator.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.operator.subroutines.pressed.lift.ElevateToPosition;
import frc.robot.operator.defaultcommands.TeleopLiftCommand;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.RobotMap;
import frc.robot.PIDCalc;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LiftSystem extends Subsystem {


    private TalonSRX rightLift;
    private TalonSRX leftLift;

    public PIDCalc pidLift;
    public double pidLiftOutput;
    
    /**
     * LiftSystem()
     */ 
    public LiftSystem() {
        //initialize talons and or victors here
        // pidLift = new PIDCalc(0.01, 0, 0, 0, "PidLift");
        // rightLift = new TalonSRX(RobotMap.rightLift);
        // leftLift = new TalonSRX(RobotMap.leftLift);

        // rightLift.follow(leftLift);
        
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopLiftCommand());
    }
    public void manualLiftControl(double power){
        //TODO: add lift encoder count soft limit here; if then command that won't allow this to be run
        //if the lift is beyond a certain encoder count
        // leftLift.set(ControlMode.PercentOutput, power);
    }
    //TODO: Move to command
    /**
     * ElevateFirstStageToPosition
     */
    public void setLiftPosition(double Position){
        // pidLiftOutput = pidLift.calculateOutput(Position, getLiftPosition());
        // leftLift.set(ControlMode.PercentOutput, pidLiftOutput);
        // SmartDashboard.putNumber("PidLiftFirstStageOutput", pidLiftOutput);
        // SmartDashboard.putBoolean("firstStageReachHeading", reachedPosition(Position));
    }

    /**
     * LiftReachedPosition
     * @param position
     * @return if Encoder counts for first stage are in range of a 100 count threshold
     */
    public boolean reachedPosition(double position){
        // double threshold = 100;
        // //Half of Threshold Value
        // double hThreshold = threshold/2;
        // return (getLiftPosition() <= (position + hThreshold) && getLiftPosition() >= (position - hThreshold));
        //Here only as placeholder. Delete whenever orignal code is uncommented
        return false;
    }

    /**
     * getLiftPosition()
     * @return Encoder count of lift
     */
    public double getLiftPosition(){
        // return leftLift.getSelectedSensorPosition(0);
        //Here only as placeholder. Delete whenever orignal code is uncommented
        return 0;
    }

    /**
     * Sets the lift Motor output to 0
     */
    public void stop(){
        // leftLift.set(ControlMode.PercentOutput, 0);
        System.out.println("Lift Motors have stopped");
    }

}
