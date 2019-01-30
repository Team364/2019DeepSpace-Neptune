package frc.robot.commands.auto.claw;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class ReturnToDefault extends Command {

    public ReturnToDefault() {
        requires(Robot.clawSystem);
        setTimeout(0.1);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        if(Robot.objState == Robot.ObjectStates.HATCH_OBJ){
            Robot.clawSystem.setObjStateHatch();
        }else if(Robot.objState == Robot.ObjectStates.CARGO_OBJ){
            Robot.clawSystem.setObjStateCargo();
        }
        
        
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.clawSystem.noClawInput();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
