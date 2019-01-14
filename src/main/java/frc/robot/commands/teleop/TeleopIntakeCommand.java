/*
 * George and Keanu:
 * This is the default intake command and will run during teleop since
 * it is the default command of IntakeSystem. Hopefully you're picking up
 * on how this works now.
 */

package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class TeleopIntakeCommand extends Command {


    /**
     * Command used for teleop control specific to the intake system
     */
    public TeleopIntakeCommand() {
        requires(Robot.intakeSystem);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
   if(Robot.oi.controller.getRawAxis(3) > 0.5) {
            Robot.intakeSystem.intake();
            System.out.println("Intake is running");
        //If the trigger is pressed lightly then the outtake for variable trigger pressure will run
        } else if(Robot.oi.controller.getRawAxis(2) > 0.2) {
            Robot.intakeSystem.outtakeForPressure();
            System.out.println("Outtake is running");
        } else {
        //If neither trigger is being pressed then the outtake will run
            Robot.intakeSystem.intakeStop();
        }

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.intakeSystem.intakeStop();
    }

}
