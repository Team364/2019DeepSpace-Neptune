package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class runIntake extends Command {
    
    private double power;
    private boolean intaking;
    public runIntake(double power, boolean intaking) {
        requires(Robot.superStructure.intake);
        this.power = power;
        this.intaking = intaking;
    }

    @Override
    protected void initialize() {
        if(intaking){
            setTimeout(2.5);
        }else{
            setTimeout(0.6);
        }
    }

    @Override
    protected void execute() {
        Robot.superStructure.intake.openLoop(power);
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
        Robot.superStructure.intake.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
