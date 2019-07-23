package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;


public class DelayedStop extends Command {
    
    private double power;
    private boolean intaking;

    public DelayedStop(double power, boolean intaking) {
        requires(Neptune.trident);
        this.power = power;
        this.intaking = intaking;
    }

    @Override
    protected void initialize() {
        if(intaking){
            setTimeout(4);
        }else{
            setTimeout(1.3);
        }
    }

    @Override
    protected void execute() {
        Neptune.trident.runIntake(power);
    }

    @Override
    protected boolean isFinished() {
        if(intaking){
            return isTimedOut() || !Neptune.trident.infrared.get();
        }else{
            return isTimedOut() ||Neptune.trident.infrared.get();
        }
    }

    @Override
    protected void end() {

       
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }
}
