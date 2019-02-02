/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.*;
import frc.robot.driver.*;
import frc.robot.operator.*;
import frc.robot.shared.autos.*;
import frc.robot.driver.subroutines.pressed.drive.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //Declarations

  //Subsystems
  public static DriveSystem driveSystem;
  public static VisionSystem visionSystem;
  public static LiftSystem liftSystem;
  public static ArmSystem armSystem;
  public static GripSystem gripSystem;
  public static ClimbSystem climbSystem;

  //Controls
  public static DriverOI oi;
  public static OperatorOI oi2;

  //Commands
  //Auto Commands
  public static Command Auto1;
  public static Command Auto2;
  public static Command Auto3;
  //Subroutine Commands
  public static Command Turn180;

  //Auto Selector String
  private String autoSelected;
  //Auto Chooser
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  //Auto Selector String Options
  private static final String driveStraightAuto = "Default";
  private static final String turnAuto = "Auto1";
  private static final String cargoAuto = "Auto2";

  public static enum ObjectStates {
    CARGO_OBJ,
    HATCH_OBJ
}

  public static ObjectStates objState = ObjectStates.CARGO_OBJ;

  @Override
  public void robotInit() {
    //Auto Selector init
    m_chooser.setDefaultOption("Default Auto", driveStraightAuto);
    m_chooser.addOption("TurnAuto", turnAuto);
    m_chooser.addOption("CargoAuto", cargoAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    //Subsystem init
    driveSystem = new DriveSystem();
    visionSystem = new VisionSystem();
    liftSystem = new LiftSystem();
    gripSystem = new GripSystem();
    armSystem = new ArmSystem();
    climbSystem = new ClimbSystem();
    //Controls init
    oi = new DriverOI();
    oi2 = new OperatorOI();
    //Auto Command inits Auto CommandGroups are assigned to commands 
    Auto1 = new TurnAuto();
    Auto2 = new CargoAuto();
    Auto3 = new StraightAuto();
    //Teleop Subroutine CommandGroups are assigned to commands
    Turn180 = new TeleopTurn180();
    //Sensors Reset
    driveSystem.resetHeading();


  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + autoSelected);
    Scheduler.getInstance().removeAll();
    driveSystem.resetHeading();
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
      Scheduler.getInstance().removeAll();
      System.out.println(objState);
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    putSmartDashVars();

  }

  @Override
  public void testPeriodic() {
  }

  private void putSmartDashVars() {
   SmartDashboard.putNumber("Gyro Angle", driveSystem.getGyroAngle());
   SmartDashboard.putNumber("GetLeftContr: ", -Robot.oi.controller.getRawAxis(5));
   SmartDashboard.putNumber("GetRightContr: ",  -Robot.oi.controller.getRawAxis(1));
}

}
