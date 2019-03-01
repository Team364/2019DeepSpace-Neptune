/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.misc.PIDCalc;

public class Align extends Command {

  private PIDCalc alignPID = new PIDCalc(0.033, 0.01, 0.0, 0.0, "Align");
  double lastTimeStamp = 0.0;
  double pidOutputNavX = 0.0;
  double desiredHeading = 0.0;

  public Align() {
    requires(Neptune.driveTrain);
    alignPID.setTolerance(1);
    setInterruptible(true);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    lastTimeStamp = 0.0;
    pidOutputNavX = 0.0;
    desiredHeading = getDesiredHeading(Neptune.vision.getCenterXValues()[0]);
    alignPID.resetPID();
    Neptune.driveTrain.zeroGyro();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // Check time... does it equal last time? If so, don't do anything
    // Otherwise, act on the information

    double newTimeStamp = Neptune.vision.getTimeStamp();
    // check current heading
    double curHeading = Neptune.driveTrain.getGyroAngle();
    System.out.println("curHeading: " + curHeading);

    if (lastTimeStamp != newTimeStamp) {
      // pidOutputNavX = alignPID.calculateOutput(480, Neptune.vision.getCenterXValues()[0]);
      // System.out.println(Neptune.vision.getCenterXValues()[0] + "hello +++++++++++==");
      // System.out.println("hello+++++++++++++++++++++++++++++++++++++++++++++++++");
      // Neptune.driveTrain.openLoop(-pidOutputNavX, pidOutputNavX);
      // System.out.println(pidOutputNavX);

      // determine desired heading
      desiredHeading = getDesiredHeading(curHeading);//curHeading + -0.1*(480 - Neptune.vision.getCenterXValues()[0]); //-ish
      System.out.println("desired heading: " + desiredHeading);
    }
    // turn to new heading
    pidOutputNavX = -1.0*alignPID.calculateOutput(desiredHeading, curHeading);
    System.out.println("in ALIGN, pidout: "+pidOutputNavX);

    Neptune.driveTrain.openLoop(-pidOutputNavX, pidOutputNavX);
    lastTimeStamp = newTimeStamp;

  }

  double getDesiredHeading(double curHeading) {
    return curHeading + 0.01*(Neptune.vision.getCenterXValues()[0] - 480);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

    if (alignPID.onTarget()) System.out.println("ALIGN DONE: ON TARGET");
    else if (Neptune.vision.getCenterXValues()[0] == 0) System.out.println("ALIGN DONE: LOST TARGET");
    return alignPID.onTarget() || (Neptune.vision.getCenterXValues()[0] == 0);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Neptune.driveTrain.stop();
    alignPID.resetPID();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
