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
import frc.robot.misc.subsystems.Piston.PistonStates;

public class Align extends Command {

  PIDCalc alignPID = new PIDCalc(0.1, 0.1, 0.0, 0.0, "Align");
  double lastTimeStamp = 0.0;
  double desiredHeading = 0.0;
  boolean desiredHeadingSet = false;
  boolean targetFound = false;
  int numCamUpdates = 0;
  static int updatesToWait = 1;

  PistonStates originalState = PistonStates.CLOSED;

  public Align() {
    this.setTimeout(1.0);
    requires(Neptune.driveTrain);
    alignPID.setTolerance(1);
    setInterruptible(true);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("ALIGN INIT");
    lastTimeStamp = Neptune.vision.getTimeStamp(); //0.0;
    desiredHeading = 0.0;
    desiredHeadingSet = false;
    targetFound = false;
    numCamUpdates = 0;

    // TODO: TUNE PIDs differently for hi/lo gear
    originalState = Neptune.driveTrain.shifter.getPistonState();
    if (originalState == PistonStates.CLOSED) {
      alignPID.setPIDParameters(0.05, 0.1, 0.0, 0.0);
    } else alignPID.setPIDParameters(0.05, 0.1, 0.0, 0.0);


    alignPID.resetPID();
    Neptune.driveTrain.zeroGyro();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println("ALIGN EXECUTE");

    // Proposed algorithm
    // 0) capture current robot state
    // 1) wait for an update (two updates?) of camera info
    // 2) calculate how far off our heading is (empirically)
    // 3) determine our new heading angle
    // 4) move robot towards that angle using PID

    // 0) capture current robot state
    double curHeading = Neptune.driveTrain.getGyroAngle();
    double newTimeStamp = Neptune.vision.getTimeStamp();
    targetFound = Neptune.vision.targetsFound();

    // 1) wait for a number of camera updates:
    // this makes sure camera is updated with target in front of us
    if (lastTimeStamp != newTimeStamp) {
      numCamUpdates++;
      lastTimeStamp = newTimeStamp;
    }

    // Have we waited long enough? If so...
    // 2) calculate how far off our heading is
    if (numCamUpdates >= updatesToWait){

      // 3) determine our new heading angle
      if (!desiredHeadingSet) {
        desiredHeading = getDesiredHeading(curHeading);
        desiredHeadingSet = true;

        //Neptune.driveTrain.shifter.setPistonState(PistonStates.OPEN);
      }

    }

    // 4) move robot towards that angle using PID
    if (desiredHeadingSet){

      double pidOut = -1.0 * alignPID.calculateOutput(desiredHeading, curHeading);
      Neptune.driveTrain.openLoop(-pidOut, pidOut);

    } else Neptune.driveTrain.stop();

    // // TODO: 5) on target, reset and do again
    // if (this.isFinished()){
    //   this.initialize();
    // }

    // // check current heading
    // double curHeading = Neptune.driveTrain.getGyroAngle();
    // System.out.println("curHeading: " + curHeading);

    // if (lastTimeStamp != newTimeStamp) {
    //   // pidOutputNavX = alignPID.calculateOutput(480, Neptune.vision.getCenterXValues()[0]);
    //   // System.out.println(Neptune.vision.getCenterXValues()[0] + "hello +++++++++++==");
    //   // System.out.println("hello+++++++++++++++++++++++++++++++++++++++++++++++++");
    //   // Neptune.driveTrain.openLoop(-pidOutputNavX, pidOutputNavX);
    //   // System.out.println(pidOutputNavX);

    //   // determine desired heading
    //   desiredHeading = getDesiredHeading(curHeading);//curHeading + -0.1*(480 - Neptune.vision.getCenterXValues()[0]); //-ish
    //   System.out.println("desired heading: " + desiredHeading);
    // }
    // // turn to new heading
    // pidOutputNavX = -1.0*alignPID.calculateOutput(desiredHeading, curHeading);
    // System.out.println("in ALIGN, pidout: "+pidOutputNavX);

    // Neptune.driveTrain.openLoop(-pidOutputNavX, pidOutputNavX);
    // lastTimeStamp = newTimeStamp;

    System.out.println("curH: "+ curHeading + " desH: " + desiredHeading);
  }

  double getDesiredHeading(double curHeading) {
    return curHeading + 0.056*(Neptune.vision.getCenterXValues()[0] - 480);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    System.out.println("ALIGN ISFINISHED");

    if (alignPID.onTarget()) System.out.println("ALIGN DONE: ON TARGET");
    // else if (Neptune.vision.getCenterXValues()[0] == 0) System.out.println("ALIGN DONE: LOST TARGET");

    return (alignPID.onTarget() && desiredHeadingSet) || !targetFound; //|| (Neptune.vision.getCenterXValues()[0] == 0);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("ALIGN END");

    // lastTimeStamp = Neptune.vision.getTimeStamp(); //0.0;
    // desiredHeading = 0.0;
    // desiredHeadingSet = false;
    // numCamUpdates = 0;
    // targetFound = false;

    //Neptune.driveTrain.shifter.setPistonState(originalState);
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
