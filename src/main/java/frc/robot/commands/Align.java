/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Neptune;
import frc.robot.misc.PIDCalc;

public class Align extends Command {

  // Number of camera updates to wait for before
  // taking any action. This prevents robot from acting
  // on stale information.
  static int updatesToWait = 3;

  PIDCalc alignPID = new PIDCalc(0,0,0,0, "Align");
  double lastTimeStamp = 0.0;
  double desiredHeading = 0.0;
  boolean desiredHeadingSet = false;
  boolean targetFound = false;
  int numCamUpdates = 0;

  public Align() {
    this.setTimeout(2.0);
    requires(Neptune.driveTrain);
    alignPID.setTolerance(1);
    setInterruptible(true);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //System.out.println("ALIGN INIT");
    lastTimeStamp = Neptune.vision.getTimeStamp();
    desiredHeading = 0.0;
    desiredHeadingSet = false;
    targetFound = false;
    numCamUpdates = 0;

    // TODO: TUNE PIDs differently for hi/lo gear
    if (Neptune.driveTrain.isShifterHigh()) {
      //Shift High
      //alignPID.setPIDParameters(0.25, 0.0, 0.15, 0);
      alignPID.setPIDParameters(0.75, 0.0, 0.0, 0);

    } else {
      //Shift Low
      //alignPID.setPIDParameters(0.05, 0.1, 0.0, 0.0);
      alignPID.setPIDParameters(0.75, 0.0, 0.0, 0);

    }
    alignPID.resetPID();
    alignPID.setOutputBoundaries(-.3, .3);

    // Neptune.driveTrain.zeroGyro();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //System.out.println("ALIGN EXECUTE");

    // Algorithm
    // 0) capture current robot state
    // 1) wait for an update (two updates?) of camera info
    // 2) calculate how far off our heading is (empirically)
    // 3) determine our new heading angle
    // 4) move robot towards that angle using PID

    // 0) capture current robot state
    // double curHeading = Neptune.driveTrain.getGyroAngle();
    double newTimeStamp = Neptune.vision.getTimeStamp();
    targetFound = Neptune.vision.targetsFound();

    // 1) wait for a number of camera updates:
    // this makes sure camera is updated with target in front of us
    if (lastTimeStamp != newTimeStamp) {
      numCamUpdates++;
      lastTimeStamp = newTimeStamp;
    }

    // Have we waited long enough? If so...
    if (numCamUpdates >= updatesToWait){

      // 2) calculate how far off our heading is, and
      // 3) determine our new heading angle
      if (!desiredHeadingSet) {
        // desiredHeading = getDesiredHeading(curHeading);
        desiredHeadingSet = true;
      }

    }

    // 4) move robot towards that angle using PID
    if (desiredHeadingSet) {
      // // double pidOut = -1.0 * alignPID.calculateOutput(desiredHeading, curHeading);
      // Neptune.driveTrain.openLoop(-pidOut, pidOut);
    } else {
      Neptune.driveTrain.stop();
    }

    // // TODO: if we want to "hold down" B button...
    // 5) on target, reset and do again
    // if (this.isFinished()){
    //   this.initialize();
    // }

    // System.out.println("curH: "+ curHeading + " desH: " + desiredHeading);
  }

  double getDesiredHeading(double curHeading) {
    // slope of 0.056 found empirically
    return curHeading + 0.056*(Neptune.vision.getCenterXValues()[0] - 480);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //System.out.println("ALIGN ISFINISHED");

    if (alignPID.onTarget()) System.out.println("ALIGN DONE: ON TARGET");
    return (alignPID.onTarget() && desiredHeadingSet) || !targetFound || isTimedOut(); //|| (Neptune.vision.getCenterXValues()[0] == 0);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    //System.out.println("ALIGN END");
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
