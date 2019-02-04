package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.defaultcommands.TeleopGripCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class GripSystem extends Subsystem {
    // private TalonSRX rightClaw;
    // private TalonSRX leftClaw;
    // //claw pistons
    // private DoubleSolenoid intakePistons;
    // //hatch pistons
    // private DoubleSolenoid leverPistons;
    // private final DigitalInput ballLimitSwitch;

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

        //ballLimitSwitch = new DigitalInput(RobotMap.ballLimitSwitch);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopGripCommand());
    }
    //These assignments are arbitrary
    //TODO: Test this
    public void runIntakeForward(){
        System.out.println("The intake is running at -1");
        // leftClaw.set(ControlMode.PercentOutput, -1);
    }
    public void runIntakeBackward(){
        System.out.println("The intake is running at 1");
        // leftClaw.set(ControlMode.PercentOutput, 1);
    }
    public void stop(){
        System.out.println("The intake has been stopped");
        // leftClaw.set(ControlMode.PercentOutput, 0);
    }
    //TODO: Make sure these are set correctly. Open should open. Close should close
    /**
     * openClaw()
     * <p>Opens Claw
     */ 
    public void openClaw() {
        System.out.println("The claw has been opened");
        // intakePistons.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * closeClaw()
     * <p>Closes Claw
     */ 
    public void closeClaw() {
        System.out.println("The claw has been closed");
        // intakePistons.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * noClawInput()
     * Leaves the claw pistons where they're at
     */ 
    public void noClawInput() {
        System.out.println("The claw has no input");
        // intakePistons.set(DoubleSolenoid.Value.kOff);
    }

    //TODO: Make sure these are set correctly. Open should open. Close should close
    //TODO: Find better name for this. Ask mechanincal what they're calling this device later on
    /**
     * openLevers()
     * <p>Opens Hatch Scoring Mechanism
     */ 
    public void openLevers() {
        System.out.println("The levers have been opened");
        // leverPistons.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * closeClaw()
     * <p>Closes Hatch Scoring Mechanism
     */ 
    public void closeLevers() {
        System.out.println("The levers have been closed");
        // leverPistons.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * noClawInput()
     * Leaves the hatch scoring pistons where they're at
     */ 
    public void noLeverInput() {
        System.out.println("The levers have no input");
        // leverPistons.set(DoubleSolenoid.Value.kOff);
    }
    //Claw is open to receive ball. Hatch apparatus opens to make room for ball
    //Do not change this. If the desired effect is what occurs, test what the
    //methods openClaw and openHatch do
    public void setObjStateCargo(){
        System.out.println("The state has been physically set to cargo");
        // openClaw();
        // openLevers();
    }
    //Hatch Apparatus closes to slide into the center of disk. Claw closes to make room for hatch
    //Do not change this. If the desired effect is what occurs, test what the
    //methods closeClaw and closeHatch do
    public void setObjStateHatch(){
        System.out.println("The state has physically been set to hatch");
        // closeClaw();
        // closeHatch();
    }
    // public boolean getBallLimitSwitch() {
    //     return ballLimitSwtich.get();
    // }
    
}


