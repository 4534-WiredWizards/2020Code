/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autos;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj.trajectory.*;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.PIDController;
import java.util.List;

public class AutoPathA extends SequentialCommandGroup {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  public AutoPathA(DifferentialDriveVoltageConstraint autoVoltageConstraint, TrajectoryConfig config) {
    /*
    * First part of the auto, does this off the starting line.
    */
    Trajectory part1 = TrajectoryGenerator.generateTrajectory(
      // Start at the origin facing the +X direction
      new Pose2d(0, 0, new Rotation2d(0)),
      // Pass through these two interior waypoints, making an 's' curve path
      List.of(
          new Translation2d(40, 40),
          new Translation2d(80, -40)
      ),
      new Pose2d(120, 0, new Rotation2d(0)),
      config
    );
    RamseteCommand ramseteCommand1 = new RamseteCommand(
        part1,
        frc.robot.RobotContainer.DrivetrainT::getPose,
        new RamseteController(DriveConstants.kRamseteB, DriveConstants.kRamseteZeta),
        new SimpleMotorFeedforward(DriveConstants.ksVolts,
                                   DriveConstants.kvVoltSecondsPerInch,
                                   DriveConstants.kaVoltSecondsSquaredPerInch),
        DriveConstants.kDriveKinematics,
        frc.robot.RobotContainer.DrivetrainT::getWheelSpeeds,
        new PIDController(DriveConstants.kPDriveVel, 0, 0),
        new PIDController(DriveConstants.kPDriveVel, 0, 0),
        frc.robot.RobotContainer.DrivetrainT::tankDriveVolts,
        frc.robot.RobotContainer.DrivetrainT
    );
    addCommands(
      ramseteCommand1.andThen(() -> frc.robot.RobotContainer.DrivetrainT.tankDriveVolts(0, 0))
    );
  }
}