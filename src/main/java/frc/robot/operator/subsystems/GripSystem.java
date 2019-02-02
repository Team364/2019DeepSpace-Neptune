package frc.robot.operator.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.operator.defaultcommands.TeleopGripCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class GripSystem extends Subsystem {
    private TalonSRX rightClaw;
    private TalonSRX leftClaw;
    //claw pistons
    private DoubleSolenoid intakePistons;
    //hatch pistons
    private DoubleSolenoid leverPistons;

    /**
     * ClawSystem()
     */ 
    public GripSystem() {
        //initialize talons and or victors here
        // rightClaw = new TalonSRX(RobotMap.rightClaw);
        // leftClaw= new TalonSRX(RobotMap.leftClaw);

        // //Telling the LeftClaw to run will make the RightClaw run inversely with the LeftClaw
        // rightClaw.follow(leftClaw);
        // rightClaw.setInverted(true);

        // intakePistons = new DoubleSolenoid(RobotMap.intakePort1, RobotMap.intakePort2);
        // leverPistons = new DoubleSolenoid(RobotMap.leverPort1, RobotMap.leverPort2);
        //TODO: Add limit switch
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopGripCommand());
    }
    //These assignments are arbitrary
    //TODO: Test this
    public void runIntakeForward(){
        // leftClaw.set(ControlMode.PercentOutput, -1);
    }
    public void runIntakeBackward(){
        // leftClaw.set(ControlMode.PercentOutput, 1);
    }
    public void stop(){
        // leftClaw.set(ControlMode.PercentOutput, 0);
    }
    //TODO: Make sure these are set correctly. Open should open. Close should close
    /**
     * openClaw()
     * <p>Opens Claw
     */ 
    public void openClaw() {
        // intakePistons.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * closeClaw()
     * <p>Closes Claw
     */ 
    public void closeClaw() {
        // intakePistons.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * noClawInput()
     * Leaves the claw pistons where they're at
     */ 
    public void noClawInput() {
        // intakePistons.set(DoubleSolenoid.Value.kOff);
    }

    //TODO: Make sure these are set correctly. Open should open. Close should close
    //TODO: Find better name for this. Ask mechanincal what they're calling this device later on
    /**
     * openLevers()
     * <p>Opens Hatch Scoring Mechanism
     */ 
    public void openLevers() {
        // leverPistons.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * closeClaw()
     * <p>Closes Hatch Scoring Mechanism
     */ 
    public void closeLevers() {
        // leverPistons.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * noClawInput()
     * Leaves the hatch scoring pistons where they're at
     */ 
    public void noLeverInput() {
        // leverPistons.set(DoubleSolenoid.Value.kOff);
    }
    //Claw is open to receive ball. Hatch apparatus opens to make room for ball
    //Do not change this. If the desired effect is what occurs, test what the
    //methods openClaw and openHatch do
    public void setObjStateCargo(){
        // openClaw();
        // openLevers();
    }
    //Hatch Apparatus closes to slide into the center of disk. Claw closes to make room for hatch
    //Do not change this. If the desired effect is what occurs, test what the
    //methods closeClaw and closeHatch do
    public void setObjStateHatch(){
        // closeClaw();
        // closeHatch();
    }
    
}


