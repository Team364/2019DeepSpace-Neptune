package frc.robot.util.prefabs.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.prefabs.subsystems.*;

/**Modes:
 * <p>0: Open
 * <p>1: Close
 * @param mode
 */
public class SetPiston extends Command {

    private Piston piston;
    private int mode;
    private boolean open;
    /**Modes:
 * <p>0: Open
 * <p>1: Close
 * @param mode
 */
    public SetPiston(Piston piston, int mode) {
        requires(piston);
        setTimeout(0.1);
        this.piston = piston;
        this.mode = mode;
    }

    @Override
    protected void initialize() {
        if(mode == 0){
            open = true;
        }else if(mode == 1){
            open = false;
        }
    }

    @Override
    protected void execute() {
        if(open){
            piston.open();
        }else{
            piston.close();
        }
         
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        piston.noInput();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }
}
