/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
// import java.math.*;
// import frc.robot.subsystems.Drivetrain;
// import frc.robot.subsystems.ExampleSubsystem;

/**
 * An example command that uses an example subsystem.
 */
public class DriveWithJoystick extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  protected double innerBound = 0.05;
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveWithJoystick() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(frc.robot.RobotContainer.DrivetrainT);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed;
    double rotation;
    //Get joystick positions and set speed and rotation to them
    //Inputs below the inner bound are ignored
    speed = Math.abs(frc.robot.RobotContainer.m_driverController.getRawAxis(1)) >= innerBound ? frc.robot.RobotContainer.m_driverController.getRawAxis(1) : 0;
    rotation = Math.abs(frc.robot.RobotContainer.m_driverController.getRawAxis(4)) >= innerBound ? frc.robot.RobotContainer.m_driverController.getRawAxis(4) : 0;
    //Go into slow speed mode if left bumper is pressed, slow rotation mode if right bumper is pressed
    speed = frc.robot.RobotContainer.m_driverController.getBumper(Hand.kLeft) ? speed * 0.5 : speed;
    rotation = frc.robot.RobotContainer.m_driverController.getBumper(Hand.kRight) ? rotation * 0.3 : rotation * 0.5;
    if (Math.abs(speed) > 0.1) rotation = rotation * 1.2;
    //Descrease speed to 0.85 normal speed, add extra 0.15 from left trigger.
    if (speed < 0) speed = speed * 0.85 - 0.15 * frc.robot.RobotContainer.m_driverController.getRawAxis(2);
    else speed = speed * 0.85 + 0.15 * frc.robot.RobotContainer.m_driverController.getRawAxis(2);
    //Only move if allowed to.
    if (frc.robot.RobotContainer.DrivetrainT.isDrivingAllowed() == true) {
        if (Math.abs(frc.robot.RobotContainer.m_driverController.getTriggerAxis(Hand.kRight)) > 0.1) frc.robot.RobotContainer.DrivetrainT.arcadeDrive(speed, rotation);
        if (Math.abs(frc.robot.RobotContainer.m_driverController.getTriggerAxis(Hand.kRight)) < 0.1) frc.robot.RobotContainer.DrivetrainT.arcadeDrive(-speed, rotation);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    frc.robot.RobotContainer.DrivetrainT.tankDrive(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
