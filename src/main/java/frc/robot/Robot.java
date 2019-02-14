package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.*;
import frc.robot.oi.*;
import frc.robot.autos.*;
import frc.robot.util.States;
/**
 * We are using Timed Robot and Command Robot together.
 * <p>The Scheduler is invoked during auto and teleop
 */
public class Robot extends TimedRobot {
  //Declarations
  //Subsystems
  public static Vision vision;
  public static SuperStructure superStructure;

  //Controls
  public static DriverOI oi;
  public static OperatorOI oi2;

  //Auto Commands
  public static Command Auto1;
  public static Command Auto2;
  public static Command Auto3;
  //Auto Selector String
  private String autoSelected;
  //Auto Chooser
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  //Auto Selector String Options
  private static final String driveStraightAuto = "Default";
  private static final String turnAuto = "Auto1";
  private static final String cargoAuto = "Auto2";

  @Override
  public void robotInit() {
    //Auto Selector init
    m_chooser.setDefaultOption("Default Auto", driveStraightAuto);
    m_chooser.addOption("TurnAuto", turnAuto);
    m_chooser.addOption("CargoAuto", cargoAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    //Subsystem init
    vision = new Vision();
    superStructure = new SuperStructure();

    //Controls init
    oi = new DriverOI();
    oi2 = new OperatorOI();

    //Auto Command inits Auto CommandGroups are assigned to commands 
    Auto1 = new TurnAuto();
    Auto2 = new CargoAuto();
    Auto3 = new StraightAuto();
    //Teleop Subroutine CommandGroups are assigned to commands
    //Sensors Reset
    superStructure.resetEncoders();

  }

  /**This runs every 20ms when the robot is enabled */
  @Override
  public void robotPeriodic() {
    Robot.superStructure.postImplementation();
  }

  /**Runs before auto */
  @Override
  public void autonomousInit() {
    autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + autoSelected);
    //Scheduler is cleared
    Scheduler.getInstance().removeAll();
  }

  @Override
  public void autonomousPeriodic() {
    switch (autoSelected) {
      case turnAuto:
        Auto1.start();
        break;
      case cargoAuto:
        Auto2.start();
        break;
      default:
        Auto3.start();
        break;
    }
    putSmartDashVars();
    Scheduler.getInstance().run();
  }
  @Override
  public void teleopInit() {
      //This removes all commands from the scheduler
      Scheduler.getInstance().removeAll();
      superStructure.resetEncoders();
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    oi2.controlLoop();
    superStructure.postSmartDashVars();
    System.out.println(Robot.superStructure.getYaw());
  }

  @Override
  public void disabledPeriodic(){

  }

  @Override
  public void testPeriodic() {
  }

  private void putSmartDashVars() {
   SmartDashboard.putNumber("GetLeftContr: ", -Robot.oi.controller.getRawAxis(5));
   SmartDashboard.putNumber("GetRightContr: ",  -Robot.oi.controller.getRawAxis(1));
   SmartDashboard.putString("Object State: ", States.objState.toString());
   SmartDashboard.putString("Loop State:", States.loopState.toString());

}

}
