package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeDrive extends Command {

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
  String shiftstate;

  public LimeDrive() {
    requires(Neptune.driveTrain);
  }

  @Override
  protected void initialize() {
    loops = 0;
    Neptune.driveTrain.setTrackingMode();
    if(Neptune.driveTrain.isShifterHigh()){
      minAim = 0.03;
      KpAim = -0.03;
      KpDistance = -0.06;
      distanceAdjustMax = 0.38;
      steerAdjustMax = 0.45;
    }else{
      minAim = 0.05;
      KpAim = -0.028;
      KpDistance = -0.07;
      distanceAdjustMax = 0.42;
      steerAdjustMax = 0.6;
    }
  }

  @Override
  protected void execute() {

    loops++;
    if(loops > 4){
      loops = 0;
      tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
      ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
      tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    }

        double headingError = -tx;
        double distanceError = -ty;
        double steerAdjust = 0.0;
        double distanceAdjust = KpDistance * distanceError;
      

        if(tv != 1){
          targetPresent = false;
        }else if(tv == 1){
          targetPresent = true;
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
        Neptune.driveTrain.openLoop(left, right*1);        
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Neptune.driveTrain.stop();
    Neptune.driveTrain.setDriverCamMode();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
