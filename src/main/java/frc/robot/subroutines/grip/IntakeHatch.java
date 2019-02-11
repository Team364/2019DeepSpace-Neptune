package frc.robot.subroutines.grip;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.*;
import frc.robot.util.States;

/**
 * Subroutine to be run in teleop on button press
 */
public class IntakeHatch extends CommandGroup {

    public IntakeHatch() {
        //Open lever
        addSequential(new SetPiston(Robot.superStructure.lever, 0));
        States.actionState = States.ActionStates.INTAKE_ACT;
    
    }
}