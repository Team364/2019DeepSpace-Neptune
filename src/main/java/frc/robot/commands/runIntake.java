package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.States;


public class RunIntake extends Command {
    
    private double power;
    private boolean intaking;

    public RunIntake(double power, boolean intaking) {
        requires(Neptune.trident);
        this.power = power;
        this.intaking = intaking;
    }

    @Override
    protected void initialize() {
        if(intaking){
            setTimeout(2.5);
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
        Neptune.trident.stopIntake();
        if(intaking){
            States.led = States.LEDstates.HAS_OBJ;
        }else{
            States.led = States.LEDstates.PASSIVE;
        }
       
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }
}
