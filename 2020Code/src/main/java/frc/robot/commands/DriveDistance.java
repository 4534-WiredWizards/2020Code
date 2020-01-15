/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
// import frc.robot.subsystems.Drivetrain;
// import com.kauailabs.navx.frc.AHRS;
// import com.kauailabs.navx.frc.AHRS.SerialDataType;
import edu.wpi.first.wpiutil.math.MathUtil;

/**
 * A command that will drive the robot to the specified distance.
 */
public class DriveDistance extends PIDCommand {
  /**
   * Drives robot to the specified distance.
   *
   */
  public DriveDistance(double targetDistanceInches) {
    super(
        new PIDController(0.05, 0.001, 0),
        // Close loop on heading
        frc.robot.RobotContainer.DrivetrainT::getEncoderAverage,
        // Set reference to target
        (targetDistanceInches + frc.robot.RobotContainer.DrivetrainT.getEncoderAverage()),
        // Pipe output to turn robot
        output -> frc.robot.RobotContainer.DrivetrainT.arcadeDrive(MathUtil.clamp(output, 0, 0.6), 0));

    // Set the controller to be continuous (because it is an angle controller)
    // getController().enableContinuousInput(-180, 180);
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController().setTolerance(2, 5);
  }

  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    return getController().atSetpoint();
  }
}