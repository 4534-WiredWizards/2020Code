/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoTest extends SequentialCommandGroup {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  public static final double DriveDist = 100;
  public static final double TurnAngle = -90;
  

  public AutoTest() {
    addCommands(
      new DriveDistance(DriveDist),
      new TurnAngle(TurnAngle),
      new DriveArc(40, 60)
    );
  }
}