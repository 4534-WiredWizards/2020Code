/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class TargetPort extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  double angle;
  double distance;
  double predictor;
  double robotVelocity;
  double ballVelocity;
  double turretAngle;
  public TargetPort() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(frc.robot.RobotContainer.ShooterT);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    frc.robot.RobotContainer.ShooterLimelightT.setLEDMode(1);
    angle = frc.robot.RobotContainer.ShooterLimelightT.getXSkew();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    turretAngle = frc.robot.RobotContainer.ShooterLimelightT.getXSkew() + frc.robot.RobotContainer.ShooterT.getAngle();
    distance = (Math.sqrt(frc.robot.RobotContainer.ShooterLimelightT.getArea()) / Math.sqrt(0.8)) * 20 * Math.sin(Math.toRadians(30)); //needs testing for equation
    robotVelocity = frc.robot.RobotContainer.DrivetrainT.getVelocity();
    ballVelocity = distance; //needs testing for equation
    predictor = Math.asin((robotVelocity * Math.sin(Math.toRadians(turretAngle))) / ballVelocity);
    angle = frc.robot.RobotContainer.ShooterLimelightT.getXSkew() + predictor;
    if (Math.abs(angle) > 0.1) frc.robot.RobotContainer.ShooterT.setTurretSpeed(angle * 0.1); //needs testing for equation
    frc.robot.RobotContainer.ShooterT.setShooterSpeed(distance * 2 / 20); //needs testing for equation
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    frc.robot.RobotContainer.ShooterLimelightT.setLEDMode(0);
    frc.robot.RobotContainer.ShooterT.setTurretSpeed(0);
    frc.robot.RobotContainer.ShooterT.setShooterSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
