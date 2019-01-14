package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.teleop.TeleopIntakeCommand;
import frc.robot.Robot;
import frc.robot.commands.teleop.*;

public class IntakeSystem extends Subsystem {

    private VictorSP leftIntake;
    private VictorSP rightIntake;


   /**
     * IntakeSystem()
     * used to intake cubes into claw for transport and outtake cubes for scoring
     */
    public IntakeSystem() {
        leftIntake = new VictorSP(RobotMap.intakeLeft);
        rightIntake = new VictorSP(RobotMap.intakeRight);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopIntakeCommand());
    }
    /**
     * intake()
     * runs the intake motors at full power
     */
    public void intake() {
        leftIntake.set(1);
        rightIntake.set(1);
    }
    /**
     * outtake()
     * runs the intake motors at full power in reverse
     */
    public void outtake() {
        leftIntake.set(-1);
        rightIntake.set(-1);
    }
    /**
     * outtakeForPressure()
     * runs the intake motors in reverse at a speed directly porportional to how far in the trigger is pushed, hence, pressure
     */  
    public void outtakeForPressure(){

            leftIntake.set(-Robot.oi.controller.getRawAxis(2));
            rightIntake.set(-Robot.oi.controller.getRawAxis(2));
    }
    /**
     * intakeStop()
     * sets the power for the intake motors to zero
     */
    public void intakeStop() {
        leftIntake.set(0);
        rightIntake.set(0);
    }
 

}