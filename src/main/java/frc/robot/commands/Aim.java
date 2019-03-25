package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;

public class Aim extends Command {
  public Aim() {
    requires(Neptune.driveTrain);
  }

  @Override
  protected void initialize() {
    Neptune.driveTrain.setTrackingMode();
  }

  @Override
  protected void execute() {
    Neptune.driveTrain.aim(Neptune.oi.controller);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Neptune.driveTrain.setDriverCamMode();
  }
  @Override
  protected void interrupted() {
    end();
  }
}
