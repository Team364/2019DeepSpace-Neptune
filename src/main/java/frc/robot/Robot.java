package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.*;
import frc.robot.oi.*;
/**
 * We are using Timed Robot and Command Robot together.
 * <p>The Scheduler is invoked during auto and teleop
 */
public class Robot extends TimedRobot {
  //Declarations
  //Subsystem
  public static SuperStructure superStructure;
  public static Elevator elevatorSystem;
  public static DriveTrain driveTrain;
  public static Trident trident;
  //Controls
  public static DriverOI oi;
  public static OperatorOI oi2;
  //Auto Commands
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

  @Override
  public void robotInit() {
    //Auto Selector init
    // m_chooser.setDefaultOption("Default Auto", driveStraightAuto);
    // m_chooser.addOption("TurnAuto", turnAuto);
    // m_chooser.addOption("CargoAuto", cargoAuto);
    // SmartDashboard.putData("Auto choices", m_chooser);

    //Subsystem init
    superStructure = new SuperStructure();
    elevatorSystem = new Elevator();
    driveTrain = new DriveTrain();
    trident = new Trident();
    //Controls init
    oi = new DriverOI();
    oi2 = new OperatorOI();
    //Auto Command inits Auto CommandGroups are assigned to commands 
    // Auto1 = new TurnAuto();
    // Auto2 = new CargoAuto();
    // Auto3 = new StraightAuto();
    //Teleop Subroutine CommandGroups are assigned to commands
    //Sensors Reset

  }

  /**This runs every 20ms when the robot is enabled */
  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
    Robot.superStructure.postImplementation();
  }

  /**Runs before auto */
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
      //This removes all commands from the scheduler
      Scheduler.getInstance().removeAll();
  }

  @Override
  public void teleopPeriodic() {
    oi2.controlLoop();
    superStructure.postSmartDashVars();
  }
  @Override
  public void disabledInit(){
    //Print Telemetry File here
  }
  @Override
  public void disabledPeriodic(){
    superStructure.postSmartDashVars();
  }

  @Override
  public void testPeriodic() {
  }
}
