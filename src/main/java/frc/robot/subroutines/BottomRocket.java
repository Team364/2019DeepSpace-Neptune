package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Neptune;
import frc.robot.States;
import frc.robot.commands.*;


public class BottomRocket extends CommandGroup{

    public BottomRocket(){

        addSequential(new ElevateToPosition(11));
        addSequential(new ActivateTrident(5));
        addSequential(new PathDrive());
        addSequential(new LimeAuto());
        addSequential(new DriveClosedLoop(2));
        //addSequential(new ActivateTrident(4));
        //addSequential(new DriveClosedLoop(-3));
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