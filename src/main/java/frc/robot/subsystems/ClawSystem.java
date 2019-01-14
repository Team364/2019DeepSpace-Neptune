package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
 //Potentiometer import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.RobotMap;
import frc.robot.commands.teleop.TeleopClawCommand;

public class ClawSystem extends Subsystem {

    private DoubleSolenoid pincher;
    private DoubleSolenoid claw;
     //Potentiometer private AnalogInput pot;

    /**
     * ClawSystem()
     * used to open and close pincher for grabbing power cubes and to flip the claw up and down about a 90 degree pivot
     */
    public ClawSystem() {
        pincher = new DoubleSolenoid(RobotMap.pinchPistonPort1, RobotMap.pinchPistonPort2);
        claw = new DoubleSolenoid(RobotMap.clawPistonPort1, RobotMap.clawPistonPort2);
     //Potentiometer pot = new AnalogInput(0);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopClawCommand());
    }
    /**
     * flipClawDown()
     * Piston on claw extends thereby flipping the claw down
     */
    public void flipClawDown() {
        claw.set(DoubleSolenoid.Value.kForward);
    }
    /**
     * flipClawUp()
     * Piston on claw retracts thereby flipping the claw up
     */
    public void flipClawUp() {
        claw.set(DoubleSolenoid.Value.kReverse);
    }
    /**
     * clawOff()
     * Claw solenoid is turned off
     */
    public void clawOff() {
        claw.set(DoubleSolenoid.Value.kOff);
    }
    /**
     * openPincher()
     * opens pincher by setting the solenoid for the piston to forward
     */
    public void openPincher() {
        pincher.set(DoubleSolenoid.Value.kForward);
    }
    /**
     * closePincher()
     * closes the pincher by setting the solenoid for the piston to reverse
     */
    public void closePincher() {
        pincher.set(DoubleSolenoid.Value.kReverse);
    }
    /**
     * pincherOff()
     * sets the picher solenoid to off
     */
    public void pincherOff() {
        pincher.set(DoubleSolenoid.Value.kOff);
    }

    /*Potentiometer
    public double getPotVoltage(){
        return  pot.getVoltage();
    }*/
    

}