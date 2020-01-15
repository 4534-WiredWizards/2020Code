/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpiutil.math.MathUtil;
// import frc.robot.subsystems.Drivetrain;
// import com.kauailabs.navx.frc.AHRS;
// import com.kauailabs.navx.frc.AHRS.SerialDataType;

/**
 * A command that will turn the robot to the specified angle.
 */
public class TurnAngle extends PIDCommand {
  /**
   * Turns to robot to the specified angle.
   *
   */
  public TurnAngle(double targetAngleDegrees) {
    super(
        new PIDController(0.02, 0.01, 0),
        // Close loop on heading
        frc.robot.RobotContainer.NavxT::getHeading,
        // Set reference to target
        (targetAngleDegrees + frc.robot.RobotContainer.NavxT.getHeading()),
        // Pipe output to turn robot
        output -> frc.robot.RobotContainer.DrivetrainT.arcadeDrive(0, MathUtil.clamp(output, 0, 0.3)));

    // Set the controller to be continuous (because it is an angle controller)
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController().setTolerance(2, 10);
  }

  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    return getController().atSetpoint();
  }

  public void end() {
    getController().close();
    frc.robot.RobotContainer.DrivetrainT.arcadeDrive(0, 0);
  }
}