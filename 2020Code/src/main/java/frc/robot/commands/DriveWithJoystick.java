package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveWithJoystick extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  protected double innerBound = 0.05;

  public DriveWithJoystick() {
    addRequirements(frc.robot.RobotContainer.DrivetrainT);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double speed;
    double rotation;
    //Get joystick positions and set speed and rotation to them
    //Inputs below the inner bound are ignored
    speed = Math.abs(frc.robot.RobotContainer.m_driverController.getRawAxis(1)) >= innerBound ? frc.robot.RobotContainer.m_driverController.getRawAxis(1) : 0;
    rotation = Math.abs(frc.robot.RobotContainer.m_driverController.getRawAxis(4)) >= innerBound ? frc.robot.RobotContainer.m_driverController.getRawAxis(4) : 0;
    //Go into slow speed mode if left bumper is pressed, slow rotation mode if right bumper is pressed
    speed = frc.robot.RobotContainer.m_driverController.getBumper(Hand.kLeft) ? speed * 0.6 : speed;
    rotation = frc.robot.RobotContainer.m_driverController.getBumper(Hand.kRight) ? rotation * 0.6 : rotation * 0.8;
    if (Math.abs(speed) > 0.1) rotation = rotation * 0.9;
    //Descrease speed to 0.85 normal speed, add extra 0.15 from left trigger.
    if (speed < 0) speed = speed * 0.85 - 0.15 * frc.robot.RobotContainer.m_driverController.getRawAxis(2);
    else speed = speed * 0.85 + 0.15 * frc.robot.RobotContainer.m_driverController.getRawAxis(2);
    //Only move if allowed to.
    if (true) {
        if (Math.abs(frc.robot.RobotContainer.m_driverController.getTriggerAxis(Hand.kRight)) > 0.1) frc.robot.RobotContainer.DrivetrainT.ArcadeDrive(speed, rotation);
        if (Math.abs(frc.robot.RobotContainer.m_driverController.getTriggerAxis(Hand.kRight)) < 0.1) frc.robot.RobotContainer.DrivetrainT.ArcadeDrive(-speed, rotation);
    }
  }

  @Override
  public void end(boolean interrupted) {
    frc.robot.RobotContainer.DrivetrainT.TankDrive(0,0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
