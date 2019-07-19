package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Neptune;
import edu.wpi.first.networktables.NetworkTableInstance;

import frc.robot.defaultcommands.DriveOpenLoop;

public class LimeDrive extends Command {

  double minAim;
  double KpAim;
  double KpDistance;
  double left;
  double right;
  double distanceAdjustMax ;
  double steerAdjustMax;
  int loops;
  double tx;
  double ty;
  double tv;
  double txShift = 1;

  DriveOpenLoop variables;


  public LimeDrive() {
    requires(Neptune.driveTrain);
  }

  @Override
  protected void initialize() {
    
    variables = new DriveOpenLoop();
    loops = 0;
    Neptune.driveTrain.setTrackingMode();
    KpAim = 0.05;//0.05
    KpDistance = -0.118;//0.118
    steerAdjustMax = 0.364;//0.364 
    distanceAdjustMax = 0.364;//0.364
  }

  @Override
  protected void execute() {

    loops++;
    if(loops > 4){
      loops = 0;
      tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0) + txShift;
      ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
      tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    }

        double headingError = tx;
        double distanceError = ty;
        double steerAdjust = 0.0;
        double distanceAdjust = 0.0;

        steerAdjust = KpAim*headingError;
        
        distanceAdjust = KpDistance * distanceError;

        steerAdjust = KpAim*headingError;

        if(distanceAdjust > distanceAdjustMax && distanceAdjust > 0){
          distanceAdjust = distanceAdjustMax;
        }else if(distanceAdjust < -distanceAdjustMax && distanceAdjust < 0){
          distanceAdjust = -distanceAdjustMax;
        }
        if (tx > 1.0){
          steerAdjust = KpAim*headingError;
      }else if (tx < 1.0){
          steerAdjust = KpAim*headingError;
      }


      if(steerAdjust > steerAdjustMax && steerAdjust > 0){
        steerAdjust = steerAdjustMax;
      }else if(steerAdjust < -steerAdjustMax && steerAdjust <0){
        steerAdjust = -steerAdjustMax;
      }
      //variables.runDrive();
      left = steerAdjust + distanceAdjust;
      right = -steerAdjust + distanceAdjust;
      Neptune.driveTrain.openLoop(left * .75, right);
      SmartDashboard.putNumber("distanceAdjust", distanceAdjust);
      SmartDashboard.putNumber("steerAdjust", steerAdjust);
      SmartDashboard.putNumber("distanceError", distanceError);
      /*
        if(steerAdjust > steerAdjustMax && steerAdjust > 0){
          steerAdjust = steerAdjustMax;
        }else if(steerAdjust < -steerAdjustMax && steerAdjust <0){
          steerAdjust = -steerAdjustMax;
        }
        
        left = steerAdjust + distanceAdjust;
        right = -steerAdjust + distanceAdjust;

        Neptune.driveTrain.openLoop(left, right*1);   */     
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

