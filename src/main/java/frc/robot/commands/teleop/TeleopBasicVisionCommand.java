package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.VisionSystem;

public class TeleopBasicVisionCommand extends Command {

    /**
     * This command ensures that values in the visionSystem are constantly updated. No vision Processing occurs here.
     */
    public TeleopBasicVisionCommand() {
        requires(Robot.visionSystem);
        setInterruptible(true);

    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() { 
        if(!Robot.visionSystem.noTarget()){
            System.out.println(Robot.visionSystem.getAngleValues()[0]);
            System.out.println(Robot.visionSystem.getCenterXValues()[0]);
            System.out.println(Robot.visionSystem.getCenterYValues()[0]);
            System.out.println(Robot.visionSystem.getHeightValues()[0]);
            System.out.println(Robot.visionSystem.getWidthValues()[0]);
        }

    }

    @Override
    protected boolean isFinished() {
       return false;
    }

    @Override
    protected void end() {
    }

}
