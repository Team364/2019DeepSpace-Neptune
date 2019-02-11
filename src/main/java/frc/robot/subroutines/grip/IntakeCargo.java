package frc.robot.subroutines.grip;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.*;
import frc.robot.util.States;

/**
 * Subroutine to be run in teleop on button press
 */
public class IntakeCargo extends CommandGroup {

    public IntakeCargo() {
        //Score Cargo Subroutine
        //Outtake 
        addSequential(new runIntake(0.5, true));
        //Close Claw
        addSequential(new SetPiston(Robot.superStructure.claw, 1));
        States.actionState = States.ActionStates.INTAKE_ACT;
    
    }
}