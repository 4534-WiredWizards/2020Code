/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.List;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
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
// import frc.robot.Constants.*;
// import edu.wpi.first.wpilibj.util.Color;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static DiskControl DiskControlT = new DiskControl();
  public static Navx NavxT = new Navx();
  public static Drivetrain DrivetrainT = new Drivetrain();
  public static Pneumatics PneumaticsT = new Pneumatics();
  public static ShooterLimelight ShooterLimelightT = new ShooterLimelight();
  public static Intake IntakeT = new Intake();
  public static Indexer IndexerT = new Indexer();
  public static Climb ClimbT = new Climb();
  public static Shooter ShooterT = new Shooter();
  //public static Lights LightsT = new Lights();

  public static XboxController m_driverController = new XboxController(0);
 
  public static Joystick m_joystick = new Joystick(1);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    DrivetrainT.setDefaultCommand(new DriveWithJoystick());
    IntakeT.setDefaultCommand(new ControlIntake());
    ShooterT.setDefaultCommand(new TurretWithJoystick());
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //final JoystickButton a = new JoystickButton(m_joystick, 1);
    //final JoystickButton b = new JoystickButton(m_joystick, 2);
    // final JoystickButton x = new JoystickButton(m_joystick, 3);
    final JoystickButton leftBumper = new JoystickButton(m_joystick, 4);

    //a.whenPressed(new GoToColor());
    //b.whenPressed(new SpinTimes());
    // x.whenPressed(new AutoTest());
    leftBumper.toggleWhenPressed(new TargetPort());
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //return new AutoTest();
    var autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(DriveConstants.ksVolts,
                                       DriveConstants.kvVoltSecondsPerInch,
                                       DriveConstants.kaVoltSecondsSquaredPerInch),
                                       DriveConstants.kDriveKinematics,
                                       10);
    // Create config for trajectory
    TrajectoryConfig config =
        new TrajectoryConfig(DriveConstants.kMaxSpeedInchesPerSecond,
                             DriveConstants.kMaxAccelerationInchesPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(DriveConstants.kDriveKinematics)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);
    // An example trajectory to follow.  All units in meters.
    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(
            new Translation2d(40, 40),
            new Translation2d(80, -40)
        ),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(120, 0, new Rotation2d(0)),
        // Pass config
        config
    );

    RamseteCommand ramseteCommand = new RamseteCommand(
        exampleTrajectory,
        DrivetrainT::getPose,
        new RamseteController(DriveConstants.kRamseteB, DriveConstants.kRamseteZeta),
        new SimpleMotorFeedforward(DriveConstants.ksVolts,
                                   DriveConstants.kvVoltSecondsPerInch,
                                   DriveConstants.kaVoltSecondsSquaredPerInch),
        DriveConstants.kDriveKinematics,
        DrivetrainT::getWheelSpeeds,
        new PIDController(DriveConstants.kPDriveVel, 0, 0),
        new PIDController(DriveConstants.kPDriveVel, 0, 0),
        // RamseteCommand passes volts to the callback
        DrivetrainT::tankDriveVolts,
        DrivetrainT
    );

    // Run path following command, then stop at the end.
    return ramseteCommand.andThen(() -> DrivetrainT.tankDriveVolts(0, 0));
  }
}
