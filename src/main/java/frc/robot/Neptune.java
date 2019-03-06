package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.*;
import frc.robot.oi.*;
import frc.robot.subroutines.ActivateTrident;
import frc.robot.States;
import frc.robot.commands.ElevateToPosition;
import frc.robot.misc.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
public class Neptune extends TimedRobot {

  public static Elevator elevator = Elevator.getInstance();
  public static DriveTrain driveTrain = DriveTrain.getInstance();
  public static Trident trident = Trident.getInstance();
  public static VisionProcessing vision = VisionProcessing.getInstance();
  public static Climber climber = Climber.getInstance();

  public static DriverOI oi;
  public static OperatorOI oi2;

  public UsbCamera camera;
  public static boolean manualControl;
  public static Command sandstorm = new ActivateTrident(5);

  @Override
  public void robotInit() {
    oi = new DriverOI();
    oi2 = new OperatorOI();

    camera = CameraServer.getInstance().startAutomaticCapture("Video", 0);
    camera.setResolution(320, 240);
    camera.setBrightness(50);
    camera.setFPS(25);

    driveTrain.zeroGyro();

  }

  @Override
  public void robotPeriodic() {

    elevator.postSmartDashVars();
    driveTrain.postSmartDashVars();
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
    Scheduler.getInstance().removeAll();

  }

  @Override
  public void teleopPeriodic() { 
    Scheduler.getInstance().run();
    oi2.controlLoop();
    postSmartDashVars();
    if ((elevator.getLiftPosition() < 10000) && (elevator.getLiftPosition() > RobotMap.liftLowerBound)) {
      States.liftZone = States.LiftZones.LOWER_DANGER;
    } else if ((elevator.getLiftPosition() > 100000) && (elevator.getLiftPosition() < RobotMap.liftUpperBound))
      States.liftZone = States.LiftZones.UPPER_DANGER;
    else {
      States.liftZone = States.LiftZones.SAFE;
    }

    if ((Neptune.elevator.getLiftPosition() >= RobotMap.liftUpperBound)) {
      elevator.stopLift();
    }
    if ((Neptune.elevator.getLiftPosition() <= RobotMap.liftLowerBound)) {
      elevator.stopLift();
    }

  //   System.out.println("tgt x: " + vision.getCenterXValues()[0] + 
  //                      " hdg: " + driveTrain.getGyroAngle() + 
  //                      " dst: " + vision.getDistanceValues()[0]);

  }

  @Override
  public void disabledInit() {
    System.out.println("DISABLED INIT");
  }

  @Override
  public void disabledPeriodic() {
    postSmartDashVars();
  }

  @Override
  public void testPeriodic() {
  }

  public void postSmartDashVars() {
    SmartDashboard.putString("Object State:", States.objState.toString());
    SmartDashboard.putString("Action State:", States.actionState.toString());
    SmartDashboard.putString("Lift Zone: ", States.liftZone.toString());
    SmartDashboard.putString("Elevator Command: ", elevator.getCurrentCommandName());
    SmartDashboard.putNumber("Elevator Target Height: ", elevator.TargetHeight);
    SmartDashboard.putNumber("Elevator Actaul Height: ", elevator.getLiftPosition());
    SmartDashboard.putNumber("Elevator Velocity: ", elevator.getLiftVelocity());
    SmartDashboard.putNumber("Arm Target Angle: ", elevator.TargetAngle);
    SmartDashboard.putNumber("Arm Actual Angle", elevator.getArmAngle());
    SmartDashboard.putNumber("Arm Velocity: ", elevator.getArmVelocity());
  }
}
