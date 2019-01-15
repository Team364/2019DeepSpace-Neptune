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

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.Autons.*;
import frc.robot.commands.teleop.Subroutines.TeleopTurn180;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static DriveSystem driveSystem;
  public static LiftSystem liftSystem;
  public static IntakeSystem intakeSystem;
  public static ClawSystem clawSystem;

  public static OI oi;

  public UsbCamera camera;
  public static Command Auto1;
  public static Command Auto2;
  public static Command Auto3;

  public static Command Turn180;

  private static final String driveStraightAuto = "Default";
  private static final String turnAuto = "Auto1";
  private static final String cargoAuto = "Auto2";
  private String autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", driveStraightAuto);
    m_chooser.addOption("TurnAuto", turnAuto);
    m_chooser.addOption("CargoAuto", cargoAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    driveSystem = new DriveSystem();
    liftSystem = new LiftSystem();
    intakeSystem = new IntakeSystem();
    clawSystem = new ClawSystem();

    oi = new OI();

    Auto1 = new TurnAuto();
    Auto2 = new CargoAuto();
    Auto3 = new StraightAuto();

    Turn180 = new TeleopTurn180();

    camera = CameraServer.getInstance().startAutomaticCapture("Video", 0);
    camera.setResolution(320, 240);
    driveSystem.resetHeading();


  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
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
}
}
