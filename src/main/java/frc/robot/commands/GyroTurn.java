package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;
import frc.robot.RobotMap;

public class GyroTurn extends Command {

    private double scalar = 18.6;
    //18.6 ticks per degree
    
    private double heading;
    private double target;

    public GyroTurn(double Heading){
        requires(Neptune.driveTrain);
        heading = Heading;
        target = heading * scalar;


        double Target = target - 870;
        double p;
        if(Target > 9 ){
            p = 0.08;
        }else{
            p = Math.abs((Target - 12) / -30);
        }
    
        Neptune.driveTrain.setPconfig(p);


        setInterruptible(true);
        setTimeout(10);
    }

    @Override
    protected void initialize(){
    }

    @Override
    protected void execute(){
        Neptune.driveTrain.turnClosedLoop(target);
        SmartDashboard.putNumber("GyroAuto", Neptune.elevator.getYaw());
    }

    @Override
    protected boolean isFinished(){
        return isTimedOut();
    }

    @Override 
    protected void interrupted(){
        super.interrupted();
    }

    @Override
    protected void end() {
        Neptune.driveTrain.setPconfig(RobotMap.drivePgain);
    }


}