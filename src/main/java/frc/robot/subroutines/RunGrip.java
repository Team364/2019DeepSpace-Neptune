package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.*;
import frc.robot.util.States;
import frc.robot.util.prefabs.commands.*;

/**
 * Subroutine to be run in teleop on button press
 */
public class RunGrip extends CommandGroup {

    private boolean cargo;
    private boolean intake;
// Can be overwritten by teams


  @Override
  protected void execute() {
      if(States.objState == States.ObjectStates.CARGO_OBJ){
        cargo = true;
      }else if(States.objState == States.ObjectStates.HATCH_OBJ){
        cargo = false;
      }
      if(States.actionState == States.ActionStates.SCORE_ACT){
          intake = false;
      }else if(States.actionState == States.ActionStates.INTAKE_ACT){
          intake = true;
      }
  }

  public RunGrip() {
    //Intake Cargo
    if(cargo && intake){
      addSequential(new runIntake(0.5, true)); //Intake
      addSequential(new SetPiston(Robot.superStructure.claw, 1)); //Close Claw
      States.actionState = States.ActionStates.PASSIVE;
    }
    //Get Hatch
    if(!cargo && intake){
      addSequential(new SetPiston(Robot.superStructure.lever, 0)); //Open lever
      States.actionState = States.ActionStates.PASSIVE;
    }
    //Score Cargo
    if(cargo && !intake){
    addSequential(new runIntake(-0.75, false)); //Outtake
    addSequential(new IntakePassive()); //Set intake to passive mode for current state
    States.actionState = States.ActionStates.PASSIVE;
    }
    //Score Hatch
    if(!cargo && !intake){
      addSequential(new SetPiston(Robot.superStructure.lever, 1)); //Close lever
      addSequential(new IntakePassive()); //Set levers to passive mode for state
      States.actionState = States.ActionStates.PASSIVE;
    }

}
}