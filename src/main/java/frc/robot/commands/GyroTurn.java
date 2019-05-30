package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.RobotMap;

public class GyroTurn extends Command {

    //TODO: figure out scalar for yaw to encoder
    private double scalar = 1;
    
    private double heading;
    private double target;

    public GyroTurn(double Heading){
        requires(Neptune.driveTrain);
        heading = Heading + Neptune.elevator.getYaw();
        target = heading * scalar;
        setInterruptible(true);
        setTimeout(0.05);
    }

    @Override
    protected void initialize(){
    }

    @Override
    protected void execute(){
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