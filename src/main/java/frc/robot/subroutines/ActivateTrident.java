package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Neptune;
import frc.robot.States;
import frc.robot.commands.*;


public class ActivateTrident extends CommandGroup {
  private int set;
  public ActivateTrident(int set) {
    this.set = set;
    if (set == 1) { // Intake Cargo
      addSequential(new DelayedStop(-0.8, true)); // Intake
      addSequential(new DelayedStop(-0.4, true)); // Intake
      addSequential(new WaitCommand(.25));      
      addSequential(new SetPiston(Neptune.trident.claw, 0)); // Close Claw
      addSequential(new ElevateToPosition(1));      
      addSequential(new WaitCommand(.25));
      addSequential(new ElevateToPosition(11));          
      addSequential(new WaitCommand(.3));
      addSequential(new inSpinIntake(), .1);
    } else if (set == 2) { // Intake Hatch
      addSequential(new SetPiston(Neptune.trident.lever, 1)); // Open lever
      addSequential(new ElevateToPosition(8));//Raise lift      
      addSequential(new StopIntake(), .1);
    } else if (set == 3) { // Score Cargo
      addSequential(new RunIntake(0.6, false)); // Outtake
      addSequential(new SetPiston(Neptune.trident.claw, 1)); // Open Claw
      addSequential(new StopIntake(), .1);
    } else if (set == 4) { // Score Hatch
      addParallel(new SetPiston(Neptune.trident.lever, 0)); // Close lever
      addSequential(new SetPiston(Neptune.trident.claw, 1));//Open Claw
      addSequential(new StopIntake(), .1);
    }else if( set == 5){// Start of Sandstorm
      addSequential(new SetPiston(Neptune.trident.lever, 1)); // Open lever
      addSequential(new ElevateToPosition(1)); //Move arm and lifts
      addSequential(new StopIntake(), .1);
    }else if(set == 6){ // Defense Mode
    
      addParallel(new SetPiston(Neptune.trident.lever, 0));
      addParallel(new SetPiston(Neptune.trident.claw, 0));
      addSequential(new ElevateToPosition(11));
      addSequential(new StopIntake(), .1);
    }
  }
  
    @Override
    protected void initialize() {
      if((set == 1)||(set == 2)){
        States.actionState = States.ActionStates.INTAKE_ACT;
      }else{
        States.led = States.LEDstates.PASSIVE;
      }
    }

    @Override
    protected void end() {
      if((set == 1)||(set == 2)){
        States.actionState = States.ActionStates.FERRY_ACT;
      }else{
        States.actionState = States.ActionStates.PASSIVE;
      }
      if(set == 2){
        States.led = States.LEDstates.HAS_OBJ;
      }else if(set ==4){
        States.led = States.LEDstates.PASSIVE;
      }
    }


}
