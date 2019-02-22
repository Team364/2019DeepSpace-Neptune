package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.*;
import frc.robot.oi.*;
import frc.robot.States;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
/**
 * We are using Timed Robot and Command Robot together.
 * <p>The Scheduler is invoked during auto and teleop
 */
public class Neptune extends TimedRobot {

  public static Elevator elevator = Elevator.getInstance();
  public static DriveTrain driveTrain = DriveTrain.getInstance();
  public static Trident trident = Trident.getInstance();

  public static DriverOI oi;
  public static OperatorOI oi2;

  public static boolean manualControl;

  public UsbCamera camera;

  // Command activeAutoCommand;
  // SendableChooser<Command> autoChooser = new SendableChooser<>();


  @Override
  public void robotInit() {
    oi = new DriverOI();
    oi2 = new OperatorOI();
    camera = CameraServer.getInstance().startAutomaticCapture("Video", 0);
    camera.setResolution(320, 240);
    //autoChooser.setDefaultOption("Default Auto", new ExampleCommand());
    //chooser.addOption("My Auto", new MyAutoCommand());
    //SmartDashboard.putData("Auto mode", autoChooser);
    Scheduler.getInstance();
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    Scheduler.getInstance().removeAll();
    // activeAutoCommand = autoChooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    // if (activeAutoCommand != null) {
    //   activeAutoCommand.start();
    // }
  }

  @Override
  public void autonomousPeriodic() {
  }
  @Override
  public void teleopInit() {
      Scheduler.getInstance().removeAll();
      // if (activeAutoCommand != null) {
      //   activeAutoCommand.cancel();
      // }
  }

  @Override
  public void teleopPeriodic() {
    postSmartDashVars();
    Scheduler.getInstance().run();
    // if((elevator.getLiftPosition() >= RobotMap.liftUpperBound)){
    //   elevator.stopLift();
    // }
    // if((elevator.getLiftPosition() <= RobotMap.liftLowerBound)){
    //   elevator.stopLift();
    // }
    
    // if((elevator.getArmAngle() >= RobotMap.armUpperBound)){
    //   elevator.stopArm();
    // }
    // if((elevator.getArmAngle() <= RobotMap.armLowerBound)){
    //   elevator.stopArm();
    // }
    // if((elevator.getLiftPosition() < 10000) &&(elevator.getLiftPosition() > RobotMap.liftLowerBound)){
    //   States.liftZone = States.LiftZones.LOWER_DANGER;
    // }else if((elevator.getLiftPosition() > 100000)&&(elevator.getLiftPosition() < RobotMap.liftUpperBound))
    //   States.liftZone = States.LiftZones.UPPER_DANGER;
    // else{
    //   States.liftZone = States.LiftZones.SAFE;
    // }
  }
  @Override
  public void disabledInit(){
  }
  @Override
  public void disabledPeriodic(){
   // postSmartDashVars();
  }

  @Override
  public void testPeriodic() {
  }
  public void postSmartDashVars(){
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
