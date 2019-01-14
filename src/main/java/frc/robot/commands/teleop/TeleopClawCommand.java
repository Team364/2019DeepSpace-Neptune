/*
 * George and Keanu:
 * This is the default intake command and will run during teleop since
 * it is the default command of clawSystem. Hopefully you're picking up
 * on how this works now.
 */

package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TeleopClawCommand extends Command {

    // Variables for claw toggle
    private int clawState;
    private boolean clawLatch;
    private int pincherState;
    private boolean pincherLatch;

    /**
     * Command used for teleop control specific to the claw system
     */
    public TeleopClawCommand() {
        requires(Robot.clawSystem);
        clawState = 0;
        clawLatch = false;
        pincherState = 0;
        pincherLatch = false;
    }

    @Override
    protected void execute() {
       
        // This is a toggle algorithm. If the button is pressed, it will
        // set the mechanism to a certain state on the first loop when the
        // button is pressed. Each loop afterwards will turn the solenoid off
        // and wait until the button is depressed.
        if(Robot.oi.clawButton.get()) {
            if(!clawLatch) {
                if(clawState == 0) {
                    Robot.clawSystem.flipClawUp();
                    clawState = 1;
                    clawLatch = true;
                     System.out.println("clawFlipped");
                    
                } else {
                    Robot.clawSystem.flipClawDown();
                    clawState = 0;
                    clawLatch = true;
                     System.out.println("clawFlipped");
                }
            } else {
                Robot.clawSystem.clawOff();
            }
        } else {
            Robot.clawSystem.clawOff();
            clawLatch = false;
        }



        if(Robot.oi.pinchButton.get()) {
            if(!pincherLatch) {
                if(pincherState == 0) {
                    Robot.clawSystem.openPincher();
                    pincherLatch = true;
                    pincherState = 1;
                   System.out.println("PincherActivated");
                } else {
                    Robot.clawSystem.closePincher();
                    pincherLatch = true;
                    pincherState = 0;
                     System.out.println("PincherActivated");
                }
            } else {
                Robot.clawSystem.clawOff();
            }
        } else {
            Robot.clawSystem.pincherOff();
            pincherLatch = false;
        }

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.clawSystem.clawOff();
        Robot.clawSystem.pincherOff();
    }

}
