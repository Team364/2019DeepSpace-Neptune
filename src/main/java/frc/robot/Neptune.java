package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.*;
import frc.robot.oi.*;
import frc.robot.States;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subroutines.*;
import frc.robot.commands.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class Neptune extends TimedRobot {

  public static Elevator elevator = Elevator.getInstance();
  public static DriveTrain driveTrain = DriveTrain.getInstance();
  public static Trident trident = Trident.getInstance();
  public static VisionProcessing vision = VisionProcessing.getInstance();

  public static DriverOI oi;
  public static OperatorOI oi2;

  public UsbCamera camera;
  // public static Command Auto1;
  // public static Command Auto2;
  // public static Command Auto3;
  // //Auto Selector String
  // private String autoSelected;
  // //Auto Chooser
  // private final SendableChooser<String> m_chooser = new SendableChooser<>();
  // //Auto Selector String Options
  // private static final String driveStraightAuto = "Default";
  // private static final String autoName = "Auto1";
  public static boolean manualControl;

  @Override
  public void robotInit() {
    // Auto Selector init
    // m_chooser.setDefaultOption("Default Auto", driveStraightAuto);
    // m_chooser.addOption("AutoName", autoName);
    // SmartDashboard.putData("Auto choices", m_chooser);
    oi = new DriverOI();
    oi2 = new OperatorOI();

    camera = CameraServer.getInstance().startAutomaticCapture("Video", 0);
    camera.setResolution(320, 240);

    // Auto1 = new AutoName();
    driveTrain.zeroGyro();

  }

  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
    elevator.postSmartDashVars();
    driveTrain.postSmartDashVars();

   // driveTrain.climbDrive.set(ControlMode.PercentOutput, 1);
  }

  @Override
  public void autonomousInit() {
    // autoSelected = m_chooser.getSelected();
    // System.out.println("Auto selected: " + autoSelected);
    Scheduler.getInstance().removeAll();
  }

  @Override
  public void autonomousPeriodic() {
    // switch (autoSelected) {
    // case autoName:
    // Auto1.start();
    // break;
    // default:
    // Auto1.start();
    // break;
    // }
  }

  @Override
  public void teleopInit() {
    Scheduler.getInstance().removeAll();

  }

  @Override
  public void teleopPeriodic() {
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

    System.out.println("tgt x: " + vision.getCenterXValues()[0] + 
                       " hdg: " + driveTrain.getGyroAngle() + 
                       " dst: " + vision.getDistanceValues()[0]);

  }

  @Override
  public void disabledInit() {
    // TODO: GOHOME command

    // Command setLever = new SetPiston(Neptune.trident.lever, 0);
    // Command setClaw = new SetPiston(Neptune.trident.claw, 0);
    // Command elevate = new ElevateToPosition(5);
    // elevate.start();
    // if(States.objState == States.ObjectStates.HATCH_OBJ){
    //   setLever = new SetPiston(Neptune.trident.lever, 0);
    //   setClaw = new SetPiston(Neptune.trident.claw, 0);
    // }else if(States.objState == States.ObjectStates.CARGO_OBJ){
    //   setLever = new SetPiston(Neptune.trident.lever, 1);
    //   setClaw = new SetPiston(Neptune.trident.claw, 1);
    // }
    // setLever.start();
    // setClaw.start();

    System.out.println("DISABLED INIT");

  }

  @Override
  public void disabledPeriodic() {
    //Scheduler.getInstance().run();
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
