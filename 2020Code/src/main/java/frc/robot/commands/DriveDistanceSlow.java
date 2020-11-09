/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpiutil.math.MathUtil;

/**
 * An example command that uses an example subsystem.
 */
public class DriveDistanceSlow extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  double m_distance = 0;
  PIDController pid = new PIDController(0.04, 0.003, 0);
  public DriveDistanceSlow(double distance) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(frc.robot.RobotContainer.DrivetrainT);
    m_distance = distance;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pid.setTolerance(5, 10);
    frc.robot.RobotContainer.DrivetrainT.allowDrive(false);
    frc.robot.RobotContainer.DrivetrainT.resetEncoders();
    frc.robot.RobotContainer.NavxT.resetHeading();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    frc.robot.RobotContainer.DrivetrainT.arcadeDrive(MathUtil.clamp(pid.calculate(frc.robot.RobotContainer.DrivetrainT.getEncoderAverage(), m_distance), -0.4, 0.4), 0/*frc.robot.RobotContainer.NavxT.getHeading() * 0.1*/);
    SmartDashboard.putNumber("Off", frc.robot.RobotContainer.DrivetrainT.getEncoderAverage());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    frc.robot.RobotContainer.DrivetrainT.arcadeDrive(0,0);
    frc.robot.RobotContainer.DrivetrainT.allowDrive(true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return pid.atSetpoint();
  }
}