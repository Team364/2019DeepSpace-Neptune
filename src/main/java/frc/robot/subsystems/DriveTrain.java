package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.subsystems.talons.BasicTalon;
import frc.robot.defaultcommands.DriveOpenLoop;
/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {

  //TODO: Change to TalonBases
  private BasicTalon leftDrive;
  private BasicTalon rightDrive;
  
  public DriveTrain(BasicTalon leftDrive, BasicTalon rightDrive){
    this.leftDrive = leftDrive;
    this.rightDrive = rightDrive;
  }

  public void openLoop(double left, double right){
    leftDrive.openLoop(left);
    rightDrive.openLoop(right);
  }
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveOpenLoop());
  }
}
