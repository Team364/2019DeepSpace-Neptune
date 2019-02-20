package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.*;
import frc.robot.oi.*;
import frc.robot.States;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * We are using Timed Robot and Command Robot together.
 * <p>The Scheduler is invoked during auto and teleop
 */
public class Neptune extends TimedRobot {

  public static Elevator elevator;
  public static DriveTrain driveTrain;
  public static Trident trident;

  public static DriverOI oi;
  public static OperatorOI oi2;

  public static Command Auto1;
  public static Command Auto2;
  public static Command Auto3;
  // //Auto Selector String
  // private String autoSelected;
  // //Auto Chooser
  // private final SendableChooser<String> m_chooser = new SendableChooser<>();
  // //Auto Selector String Options
  // private static final String driveStraightAuto = "Default";
  // private static final String turnAuto = "Auto1";
  // private static final String cargoAuto = "Auto2";
  public static boolean manualControl;

  @Override
  public void robotInit() {
    //Auto Selector init
    // m_chooser.setDefaultOption("Default Auto", driveStraightAuto);
    // m_chooser.addOption("TurnAuto", turnAuto);
    // m_chooser.addOption("CargoAuto", cargoAuto);
    // SmartDashboard.putData("Auto choices", m_chooser);

    elevator = new Elevator();
    driveTrain = new DriveTrain();
    trident = new Trident();

    oi = new DriverOI();
    oi2 = new OperatorOI();

    // Auto1 = new TurnAuto();
    // Auto2 = new CargoAuto();
    // Auto3 = new StraightAuto();


  }

  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    // autoSelected = m_chooser.getSelected();
    // System.out.println("Auto selected: " + autoSelected);
    //Scheduler is cleared
    Scheduler.getInstance().removeAll();
  }

  @Override
  public void autonomousPeriodic() {
    // switch (autoSelected) {
    //   case turnAuto:
    //     Auto1.start();
    //     break;
    //   case cargoAuto:
    //     Auto2.start();
    //     break;
    //   default:
    //     Auto3.start();
    //     break;
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
    if((elevator.getLiftPosition() < 10000) &&(elevator.getLiftPosition() > RobotMap.liftLowerBound)){
      States.liftZone = States.LiftZones.LOWER_DANGER;
    }else if((elevator.getLiftPosition() > 100000)&&(elevator.getLiftPosition() < RobotMap.liftUpperBound))
      States.liftZone = States.LiftZones.UPPER_DANGER;
    else{
      States.liftZone = States.LiftZones.SAFE;
    }

    if((Neptune.elevator.getLiftPosition() >= RobotMap.liftUpperBound)){
      elevator.stopLift();
    }
    if((Neptune.elevator.getLiftPosition() <= RobotMap.liftLowerBound)){
      elevator.stopLift();
    }
  }
  @Override
  public void disabledInit(){
  }
  @Override
  public void disabledPeriodic(){
    postSmartDashVars();
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
