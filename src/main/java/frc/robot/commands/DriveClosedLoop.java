package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;
import frc.robot.RobotMap;
import frc.robot.States;


public class DriveClosedLoop extends Command {
    
    //parameter switched to this variable for transfer to motion magic
    private double target;
    private double p;

    public DriveClosedLoop(double Target) {
        requires(Neptune.driveTrain);
        target = Target * -870;
        if(Target > 9 ){
            p = 0.08;
        }else{
            p = Math.abs((Target - 12) / -30);
        }
    
        Neptune.driveTrain.setPconfig(p);
        setInterruptible(false);
        setTimeout(1);
    }

    @Override
    protected void initialize() {
        //2609 ticks = 1 foot        
        Neptune.driveTrain.resetEncoders();
    }

    @Override
    protected void execute() {
        Neptune.driveTrain.getRightDrive().getClosedLoopTarget();
        Neptune.driveTrain.closedLoop(target);
    }

    @Override
    protected boolean isFinished() {
        if((Math.abs(Neptune.driveTrain.getLeftError()) < 200 && Math.abs(Neptune.driveTrain.getRightError()) < 200) && isTimedOut()){
            return true;
        }else{
            return false;
        }
    }
    @Override
    protected void end() {
        Neptune.driveTrain.setPconfig(RobotMap.drivePgain);
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }
}
