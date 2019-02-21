package frc.robot.defaultcommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;
import frc.robot.RobotMap;
import frc.robot.States;

public class ElevatorManual extends Command {

  private double liftPower;
  private double armPower;
  private double adjustedLiftPosition;
  private double adjustedArmAngle;

  public ElevatorManual() {
    requires(Neptune.elevator);
    setInterruptible(true);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {

    liftPower = -Neptune.oi2.controller2.getRawAxis(1);
    armPower = Neptune.oi2.controller2.getRawAxis(5);
    if (liftPower > 0.1) {
      if (!Neptune.manualControl) {
        adjustedLiftPosition = Neptune.elevator.getLiftPosition();
        Neptune.manualControl = true;
      }
      adjustedLiftPosition += 700;
    } else if (liftPower < -0.1) {
      adjustedLiftPosition -= 700;
    }
    if (Neptune.manualControl) {
      Neptune.elevator.setLiftPosition(adjustedLiftPosition);
    }
    SmartDashboard.putNumber("Adjusted Position", adjustedLiftPosition);
  }

  @Override
  protected void interrupted() {
    end();
  }

  @Override
  protected void end() {
  }

  @Override
  protected boolean isFinished() {
    /*
     * This command will only end when interrupted during teleop mode by buttons in
     * the Operator Interface
     */
    return false;
  }

}
