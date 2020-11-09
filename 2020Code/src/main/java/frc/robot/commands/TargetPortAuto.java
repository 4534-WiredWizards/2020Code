/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.controller.PIDController;

/**
 * An example command that uses an example subsystem.
 */
public class TargetPortAuto extends CommandBase {
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
  long time;
  boolean m_aim;
  boolean m_rev;
  PIDController pid = new PIDController(0.1, 0, 0.0);
  public TargetPortAuto(boolean aim, boolean rev) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(frc.robot.RobotContainer.ShooterT);
    m_aim = aim;
    m_rev = rev;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    frc.robot.RobotContainer.ShooterLimelightT.setLEDMode(3);
    angle = frc.robot.RobotContainer.ShooterLimelightT.getXSkew();
    frc.robot.RobotContainer.ShooterLimelightT.setPipeline(1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    distance = (95 - 17) / Math.tan((frc.robot.RobotContainer.ShooterLimelightT.getYSkew() + 22) * Math.PI/ 180);
    if(m_rev) frc.robot.RobotContainer.ShooterT.setShooterVoltage(9);
    else frc.robot.RobotContainer.ShooterT.setShooterVoltage(0);
    frc.robot.RobotContainer.ShooterT.setHood(-0.137+0.005299*distance-0.0000113*(Math.pow(distance, 2)));
    if(frc.robot.RobotContainer.ShooterLimelightT.limelightHasTarget() && m_aim) {
      angle = frc.robot.RobotContainer.ShooterLimelightT.getXSkew() + 1.2;
      frc.robot.RobotContainer.ShooterT.setTurretSpeed(pid.calculate(angle, 0));
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    frc.robot.RobotContainer.ShooterLimelightT.setLEDMode(0);
    frc.robot.RobotContainer.ShooterT.setTurretSpeed(0);
    frc.robot.RobotContainer.ShooterLimelightT.setPipeline(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
