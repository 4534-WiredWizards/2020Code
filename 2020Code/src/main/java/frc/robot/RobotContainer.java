package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
  public static Drivetrain DrivetrainT = new Drivetrain();
  public static XboxController m_driverController = new XboxController(0);

  public RobotContainer() {
    DrivetrainT.setDefaultCommand(new DriveWithJoystick());
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
