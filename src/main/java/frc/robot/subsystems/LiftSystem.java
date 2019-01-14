/*
 * George and Keandre:
 * This is the LiftSystem class. It holds objects for all of the Talons, Victors, and
 * limit switches. We can add more sensors as we iterate the robot design over the offseason.
 */

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.teleop.TeleopLiftCommand;
import frc.robot.PIDCalc;

// Class declaration : extends Subsystem
public class LiftSystem extends Subsystem {

    /**
     * Create all object names here.
     */
    public final VictorSP firstStage1;
    public final VictorSP firstStage2;
    public final VictorSP secondStage1;
    public final VictorSP secondStage2;
    public PIDCalc pidLift;
    public double pidOutputLift;
    public int liftCountError;

    //public final DigitalInput firstStageTopLimit;
    //public final DigitalInput firstStageBottomLimit;
    //public final DigitalInput secondStageTopLimit;
    //public final DigitalInput secondStageBottomLimit;

    /**
     * LiftSystem()
     * -Constructor-
     * This function is called when you create a new instance
     * of this subsystem (= new Liftsystem();). This sets all
     * of the port numbers for each motor controller/solenoid.
     */
    public LiftSystem() {
        firstStage1 = new VictorSP(RobotMap.firstStage1);
        firstStage2 = new VictorSP(RobotMap.firstStage2);

        secondStage1 = new VictorSP(RobotMap.secondStage1);
        secondStage2 = new VictorSP(RobotMap.secondStage2);
        
        pidLift = new PIDCalc(0.0004, 0, 0, 0, "Lift"); //0.0003, 0, 0 , 0, "Lift"

        //firstStageTopLimit = new DigitalInput(RobotMap.firstStageTopLimit);
        //firstStageBottomLimit = new DigitalInput(RobotMap.secondStageBottomLimit);

        //secondStageTopLimit = new DigitialInput(3);
        //secondStageBottomLimit = new DigitalInput(2);
    }

    /**
     * initDefaultCommand()
     * This function sets the default command to be run when there
     * are no other commands scheduled for this subsystem.
     * Put a TeleopCommand here.
     */
    @Override
    protected void initDefaultCommand(){
        setDefaultCommand(new TeleopLiftCommand());
    }


    /**
     * firstStageControl(double speed)
     * @param speed This is a double type that appliess a speed to the motors.
     * The limit switches will tell the lift to not move further in that
     * direction if they are triggered.
     */
    public void firstStageControl(double speed) {
        /*
        if (getFirstStageTopLimit()) {
            if(speed < 0) {
                firstStage1.set(speed);
            }
        } else if(getFirstStageBottomLimit()) {
            if (speed > 0) {
                firstStage1.set(speed);
            }
        } else {
            firstStage1.set(0);
        }
        */
        firstStage1.set(speed);
        firstStage2.set(speed);
    }

    /**
     * secondStageControl(double speed)
     * @param speed This is a double type that applies a speed to the motors.
     * The limit switches will tell the lift to not move further in that
     * direction if they are triggered.
     */
    public void secondStageControl(double speed) {
        /*
        if (getSecondStageTopLimit()) {
            if(speed < 0) {
                secondStage1.set(speed);
            }
        } else if(getSecondStageBottomLimit()) {
            if (speed > 0) {
                secondStage1.set(speed);
            }
        } else {
            secondStage1.set(0);
        }
        */
        secondStage1.set(speed);
        secondStage2.set(speed);
    }

    /**
     * keepFirstStagePosition(int counts)
     * @param counts This is an int that tells the lift to stay at a certain encoder count.
     * When the lift is not being told to move, it will be told to keep its current position.
     */
    /*
    public void keepFirstStagePosition(int counts) { 
        pidOutputLift = pidLift.calculateOutput(counts + liftCountError, getEncoderCounts());  
        firstStage1.set(-pidOutputLift);
        firstStage2.set(-pidOutputLift);
        secondStage1.set(0);
        secondStage2.set(0);
    }
*/
    /**
     * getEncoderCounts()
     * @return returns current encoder counts of the first stage.
     */
    /*
    public int getEncoderCounts() {
        return firstStage1.getSelectedSensorPosition(0);
    }
    */
    /**
     * resetEncoders()
     * sets firstStageEncoders to zero
     */
    /*
    public void resetEncoders() {
        firstStage1.setSelectedSensorPosition(0, 0, 0);
    }
    */
    /**
     * getFirstStageTopLimit()
     * @return returns the first stage top limit switch position.
     */
    /*
    public boolean getFirstStageTopLimit() {
        return firstStageTopLimit.get();
    }
    */
    /**
     * getFirstStageBottomLimit()
     * @return returns the first stage bottom limit switch position.
     */
    /*
    public boolean getFirstStageBottomLimit() {
        return firstStageBottomLimit.get();
    }
    */
    /**
     * getSecondStageTopLimit()
     * @return returns the second stage top limit switch position.
     */
    /*
    public boolean getSecondStageTopLimit() {
        return secondStageTopLimit.get();
    }
    */

    /**
     * getSecondStageBottomLimit()
     * @return returns the second stage bottom limit switch position.
     */
    /*
    public boolean getSecondStageBottomLimit() {
        return secondStageBottomLimit.get();
    }
    */
    /**
     * stopBoth()
     * Stops both lift motors.
     */

    public void stopBoth() {
        firstStage1.set(0);
        secondStage1.set(0);
        firstStage2.set(0);
        secondStage2.set(0);
    }

}
