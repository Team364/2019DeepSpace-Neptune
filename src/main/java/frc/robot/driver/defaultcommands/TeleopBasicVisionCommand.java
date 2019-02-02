package frc.robot.driver.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TeleopBasicVisionCommand extends Command {

    /**
     * Command that runs automatically for visionSystem
     * Prints Vision Target Data to Console
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
        //If a target is seen, output the values for angle, center x and y, height, and width
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
