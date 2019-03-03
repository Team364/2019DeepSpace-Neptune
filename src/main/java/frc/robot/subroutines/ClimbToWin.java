/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Neptune;
import frc.robot.commands.*;

public class ClimbToWin extends CommandGroup {
  
	public ClimbToWin() {
		addSequential(new ElevateToPosition(6));
		addSequential(new WaitCommand(2));
		addSequential(new SetPiston(Neptune.elevator.front, 1));
		addSequential(new WaitCommand(2));
		addParallel(new ElevateToPosition(7));
		addSequential(new ClimbWithGyro(0));
		addSequential(new ClimbWithGyro(-5));
		addSequential(new DriveClimberForwards());
	}

}
