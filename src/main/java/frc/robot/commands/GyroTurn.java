package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.RobotMap;

public class GyroTurn extends Command {

    //TODO: figure out yaw for yaw to encoder
    private double scalar;
    
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
    }

    @Override
    protected void execute(){
        target = scalar * (Neptune.elevator.getYaw() - heading);
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