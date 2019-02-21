package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;


public class runIntake extends Command {
    
    private double power;
    private boolean intaking;
    public runIntake(double power, boolean intaking) {
        requires(Neptune.trident);
        this.power = power;
        this.intaking = intaking;
    }

    @Override
    protected void initialize() {
        if(intaking){
            setTimeout(1);
        }else{
            setTimeout(0.6);
        }
    }

    @Override
    protected void execute() {
        Neptune.trident.runIntake(power);
    }

    @Override
    protected boolean isFinished() {
        // if(intaking){
        //     return isTimedOut() || Robot.superStructure.limitArray[0];
        // }else{
        //     return isTimedOut();
        // }
        return isTimedOut();
    }

    @Override
    protected void end() {
        Neptune.trident.stopIntake();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
