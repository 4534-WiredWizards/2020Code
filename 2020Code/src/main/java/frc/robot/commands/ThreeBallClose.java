/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ThreeBallClose extends SequentialCommandGroup {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  public ThreeBallClose() {
    addCommands(
      new AutoControlIntake(0, 0, true).withTimeout(1),
      new ParallelCommandGroup(
        new DriveDistance(10),
        new TargetPortAuto(false, true).withTimeout(2)
      ),
      new TargetPortAuto(true, true).withTimeout(0.5),
      new ParallelCommandGroup(
        new TargetPortAuto(true, true).withTimeout(3),
        new AutoControlIntake(0, -0.8, true).withTimeout(3)
      ),
      new AutoControlIntake(0, 0, true).withTimeout(0.1),
      new TargetPortAuto(false, false).withTimeout(0.1)
    );
  }
}