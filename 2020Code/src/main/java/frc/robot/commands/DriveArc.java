/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpiutil.math.MathUtil;

/**
 * An example command that uses an example subsystem.
 */
public class DriveArc extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  double m_radius = 0;
  double m_angle = 0;
  double wheelDistance = 21.0;
  double output;
  double target;
  PIDController pid = new PIDController(0.0000003, 0, 0);
  public DriveArc(double radius, double angle) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(frc.robot.RobotContainer.DrivetrainT);
    m_radius = radius;
    m_angle = angle;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pid.setTolerance(2, 10);
    frc.robot.RobotContainer.DrivetrainT.allowDrive(false);
    frc.robot.RobotContainer.DrivetrainT.resetEncoders();
    output = 0.6 / (m_radius+wheelDistance/2) * (m_radius-wheelDistance/2);
    System.out.println("hello world");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_angle > 0){
      target = (frc.robot.RobotContainer.DrivetrainT.getLeftVelocity() / (m_radius + (wheelDistance/2))) * (m_radius - (wheelDistance/2));
      //output += MathUtil.clamp(pid.calculate(frc.robot.RobotContainer.DrivetrainT.getRightVelocity(),target), -0.6, 0.6);
      frc.robot.RobotContainer.DrivetrainT.tankDrive(0.6, output);
    }
    else{
      target = (frc.robot.RobotContainer.DrivetrainT.getRightVelocity() / (m_radius + (wheelDistance/2))) * (m_radius - (wheelDistance/2));
      //output += MathUtil.clamp(pid.calculate(frc.robot.RobotContainer.DrivetrainT.getLeftVelocity(), target), -0.6, 0.6);
      frc.robot.RobotContainer.DrivetrainT.tankDrive(output, 0.6);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    frc.robot.RobotContainer.DrivetrainT.arcadeDrive(0,0);
    frc.robot.RobotContainer.DrivetrainT.allowDrive(true);
    System.out.println("goodbye world");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(frc.robot.RobotContainer.DrivetrainT.getEncoderAverage()) >= m_radius * Math.PI * 2 * Math.abs(m_angle)/360;
  }
}