package frc.robot.commands.teleop.buttonsubroutines.claw;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.auto.drive.*;
import frc.robot.commands.auto.claw.*;

/**
 * Subroutine to be run in teleop on button press
 */
public class IntakeObject extends CommandGroup {
    /**
     * Uses Object State to determine which subroutine to run to intake
     */
    public IntakeObject() {
        if(Robot.objState == Robot.ObjectStates.HATCH_OBJ){
            //Score Hatch Subroutine
            addSequential(new OpenHatchMechanism());
        }else if(Robot.objState == Robot.ObjectStates.CARGO_OBJ){
            //Score Cargo Subroutine
            addSequential(new IntakeCargo());
            addSequential(new CloseClaw());
        }

    
    }
}