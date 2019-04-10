package frc.robot.auto.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;

public class RampDrive extends Command {

  double currentSpeedR;
  double currentSpeedL;
  double maxSpeedR;
  double maxSpeedL;
  double timeOut;
  double timeEl = 0;
  boolean backwards;

  public RampDrive(double maxSpeedL, double maxSpeedR, double timeOut){
    requires(Neptune.driveTrain);
    setTimeout(timeOut);
    this.maxSpeedR = maxSpeedR;
    this.maxSpeedL = maxSpeedL;
    this.timeOut = timeOut;
  }

  @Override
  protected void initialize() {
    currentSpeedR = 0;
    currentSpeedL = 0;

  }

  @Override
  protected void execute() {
    timeEl = timeSinceInitialized();
    currentSpeedL = (maxSpeedL / 2) * Math.sin((((360*timeEl)/timeOut)-90)*Math.PI/180) + (maxSpeedL / 2);
    currentSpeedR = (maxSpeedR / 2) * Math.sin((((360*timeEl)/timeOut)-90)*Math.PI/180) + (maxSpeedR / 2);
    Neptune.driveTrain.openLoop(currentSpeedL, currentSpeedR*0.85);
    SmartDashboard.putNumber("ramDR", currentSpeedR);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Neptune.driveTrain.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
