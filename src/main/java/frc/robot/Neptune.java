package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.oi.DriverOI;
import frc.robot.oi.OperatorOI;
import frc.robot.subroutines.ActivateTrident;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.SwerveMod;
//import frc.robot.subsystems.SwerveMod;
import frc.robot.subsystems.Trident;

public class Neptune extends TimedRobot {

  public static AnalogOutput LEDs = new AnalogOutput(0);
  public static Elevator elevator = Elevator.getInstance();
  public static DriveTrain driveTrain = DriveTrain.getInstance();
  public static Trident trident = Trident.getInstance();
  public static Climber climber = Climber.getInstance();

  public static DriverOI oi;
  public static OperatorOI oi2;

  public UsbCamera camera;
  public static Command sandstorm = new ActivateTrident(5);
  public static Command gyroStart = new ResetGyro();
  public static double teleopStart;
  public static double teleopElapsedTime;
  public static boolean endGame;
  public static boolean climbDrive;
  public int stopLoops;

  private DriverStation dStation = DriverStation.getInstance(); //leds
  public static RobotController diagnostics;
 

  @Override
  public void robotInit() {
    oi = new DriverOI();
    oi2 = new OperatorOI();
    camera = CameraServer.getInstance().startAutomaticCapture("Video", 0);
    camera.setResolution(320, 240);
    camera.setFPS(18);
    gyroStart.start();
  } 

  @Override
  public void robotPeriodic() {
    
    SmartDashboard.putNumber("gyro", Neptune.elevator.getYaw());
    for(SwerveMod mod : driveTrain.getSwerveModules()){
      SmartDashboard.putNumber("Module Angle " + mod.moduleNumber + "  ", mod.getPos());
      SmartDashboard.putNumber("Target Angle " + mod.moduleNumber + "  ", mod.getTargetAngle());
    }
    
  }

  @Override
  public void autonomousInit() {
    Scheduler.getInstance().removeAll();
    sandstorm.start();
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    oi2.controlLoop();
  }

  @Override
  public void teleopInit() {
    Neptune.driveTrain.setDriverCamMode();
    Scheduler.getInstance().removeAll();
  }

  @Override
  public void teleopPeriodic() { 
    Scheduler.getInstance().run();
    oi2.controlLoop();
    double elevatorLiftPosition = elevator.getLiftPosition();
    if ((elevatorLiftPosition < 10000) && (elevatorLiftPosition > RobotMap.liftLowerBound)) {
      States.liftZone = States.LiftZones.LOWER_DANGER;
    } else if ((elevatorLiftPosition > 100000) && (elevatorLiftPosition < RobotMap.liftUpperBound))
      States.liftZone = States.LiftZones.UPPER_DANGER;
    else {
      States.liftZone = States.LiftZones.SAFE;
    }

    if ((elevatorLiftPosition >= RobotMap.liftUpperBound)) {
      if(stopLoops == 0){
        elevator.stopLift();
        stopLoops++;
      }
      elevator.setLiftPosition(RobotMap.liftHighH);

    }
    if ((elevatorLiftPosition <= RobotMap.liftLowerBound)) {
      if(stopLoops == 0){
        elevator.stopLift();
        stopLoops++;
      }
      elevator.setLiftPosition(0);
  
    }

    
  }

}
