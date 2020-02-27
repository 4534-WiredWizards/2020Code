/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;

/**
 * An example command that uses an example subsystem.
 */
public class TurretWithJoystick extends CommandBase {
  double hoodOut;
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public TurretWithJoystick() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(frc.robot.RobotContainer.ShooterT);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      frc.robot.RobotContainer.ShooterT.setTurretSpeed(-frc.robot.RobotContainer.m_joystick.getRawAxis(0));
      hoodOut = frc.robot.RobotContainer.ShooterT.getHood();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    frc.robot.RobotContainer.ShooterT.setTurretSpeed(frc.robot.RobotContainer.m_joystick.getRawAxis(0));
    if(frc.robot.RobotContainer.m_joystick.getRawButton(5)) frc.robot.RobotContainer.ShooterT.setShooterVoltage(9);
    else frc.robot.RobotContainer.ShooterT.setShooterVoltage(0);
    frc.robot.RobotContainer.ShooterT.setHood(hoodOut);
    if(Math.abs(frc.robot.RobotContainer.m_joystick.getRawAxis(5)) > 0.1) hoodOut -= frc.robot.RobotContainer.m_joystick.getRawAxis(5) * 0.003;
    hoodOut = MathUtil.clamp(hoodOut, 0.0, 0.7);
    SmartDashboard.putNumber("Hood pos", frc.robot.RobotContainer.ShooterT.getHood());
    SmartDashboard.putNumber("Hood out", hoodOut);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
