package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Neptune;
import frc.robot.States;
import frc.robot.commands.*;


public class BottomRocket extends CommandGroup{

    public BottomRocket(){
        //would recommend NOT trying this yet

        //like seriously its no bueno right now

        //addSequential(new PathDrive(0));
        //addSequential(new PathDrive(1));
        //addSequential(new ElevateToPosition(11));
    addSequential(new DriveClosedLoop(18));
        //addSequential(new GyroTurn(90));
        //addSequential(new PathDrive());


    }

    @Override
    protected void initialize(){

    }

    @Override
    protected void end(){

    }

}