package frc.robot.auto.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeAuto extends Command {

  double minAim;
  double KpAim;
  double KpDistance;
  double left;
  double right;
  double distanceAdjustMax ;
  double steerAdjustMax;
  boolean targetPresent = false;
  int loops;
  double tx;
  double ty;
  double tv;
  double ta;
  double steerAdjust = 0.0;
  double distanceAdjust = 0.0;
  int timeLoops;

  public LimeAuto() {
    requires(Neptune.driveTrain);
  }

  @Override
  protected void initialize() {
    loops = 0;
    Neptune.driveTrain.setTrackingMode();
    if(Neptune.driveTrain.isShifterHigh()){
      minAim = 0.03;
      KpAim = -0.023;
      KpDistance = -0.065;
      distanceAdjustMax = 0.38;
      steerAdjustMax = 0.45;
    }else{
      minAim = 0.05;
      KpAim = -0.028;
      KpDistance = -0.08;
      distanceAdjustMax = 0.42;
      steerAdjustMax = 0.6;
    }
  }

  @Override
  protected void execute() {
    timeLoops++;
    loops++;
    if(loops > 4){
      loops = 0;
      tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
      ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
      tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
      ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

    }

        double headingError = -tx;
        double distanceError = -ty;
        distanceAdjust = KpDistance * distanceError;
      

        if(tv != 1){
          targetPresent = false;
          System.out.println("No target in sight");
        }else if(tv == 1){
          targetPresent = true;
          System.out.println("Target is in sight");
        }

        if (tx > 1.0){
            steerAdjust = KpAim*headingError - minAim;
        }else if (tx < 1.0){
            steerAdjust = KpAim*headingError + minAim;
        }
        
        if(distanceAdjust > distanceAdjustMax && distanceAdjust > 0){
          distanceAdjust = distanceAdjustMax;
        }else if(distanceAdjust < -distanceAdjustMax && distanceAdjust < 0){
          distanceAdjust = -distanceAdjustMax;
        }
        if(steerAdjust > steerAdjustMax && steerAdjust > 0){
          steerAdjust = steerAdjustMax;
        }else if(steerAdjust < -steerAdjustMax && steerAdjust <0){
          steerAdjust = -steerAdjustMax;
        }
        left = steerAdjust + distanceAdjust;
        right = -steerAdjust + distanceAdjust;
        Neptune.driveTrain.openLoop(left, right);

        SmartDashboard.putNumber("Limelight Heading Error", tx);
        SmartDashboard.putNumber("Limelight Distance Error", ty);
        SmartDashboard.putNumber("Linelight Steering Adjust", steerAdjust);
        SmartDashboard.putNumber("Limelight Distance Adjust", distanceAdjust);
        SmartDashboard.putNumber("Limelight Left", left);
        SmartDashboard.putNumber("Limelight right", right);
        SmartDashboard.putNumber("Limelight v", tv);
        SmartDashboard.putBoolean("Target Processed", targetPresent);
        
  }

  @Override
  protected boolean isFinished() {
    return (ta > 7.0) && (timeLoops > 50);
  }

  @Override
  protected void end() {
    Neptune.driveTrain.stop();
    timeLoops = 0;
  }

  @Override
  protected void interrupted() {
    end();
  }
}
