package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.RobotMap;

public class GyroTurn extends Command {

    //coefficient that yaw is multiplied by to get correct number for motion magic
    //TODO: figure out coefficient for yaw to encoder
    private double coefficient;
    
    private double heading;
    private double target;

    public GyroTurn(double Heading){
        requires(Neptune.driveTrain);
        heading = Heading;
        setInterruptible(true);
        setTimeout(0.05);
    }

    @Override
    protected void initialize(){
        Neptune.manualControl = false;
    }

    @Override
    protected void execute(){
        target = coefficient * (Neptune.driveTrain.getYaw() - heading);
        Neptune.driveTrain.turnClosedLoop(target);
    }

    @Override
    protected boolean isFinished(){
        return isTimedOut();
    }

    @Override 
    protected void interrupted(){
        super.interrupted();
    }


}