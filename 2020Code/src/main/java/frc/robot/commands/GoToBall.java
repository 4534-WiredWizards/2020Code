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
public class GoToBall extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  double angle = 0;
  double size = 0;
  boolean followOffscreen;
  double lastKnownPosition;
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public GoToBall(boolean followOffscreenT) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(frc.robot.RobotContainer.DrivetrainT);
    followOffscreen = followOffscreenT;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    frc.robot.RobotContainer.BallLimelightT.setPipeline(1);
    frc.robot.RobotContainer.DrivetrainT.allowDrive(false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    angle = frc.robot.RobotContainer.BallLimelightT.getXSkew();
    size = frc.robot.RobotContainer.BallLimelightT.getYellowBoxHorizontal();
    float speed = 0;
    float rotation = 0;
    //If angle is high, turn right at propprtional speed
    if (angle > 1) {
        rotation = (float)(angle/22.5 * 0.5 + 0.5);
    }
    //If angle is low, turn left at proportional speed
    else if (angle < -1) {
        rotation = (float)(angle/22.5 * 0.5 - 0.5);
    }
    //If the size is less than 120, move closer.
    if (size < 120) {
        speed = (float)0.6;
    }
    //Go to cone if it is there
    if(frc.robot.RobotContainer.BallLimelightT.limelightHasTarget()) {
      frc.robot.RobotContainer.DrivetrainT.arcadeDrive(speed, rotation);
    }
    //if the cone isnt there, but we are in follow offscreen mode, 
    //and the cone was most recently at an angle near the edge, turn in that direction without moving forward.
    if(followOffscreen) {
        if(lastKnownPosition < -15 && !frc.robot.RobotContainer.BallLimelightT.limelightHasTarget()) frc.robot.RobotContainer.DrivetrainT.arcadeDrive(0, -0.5);
        if(lastKnownPosition < 15 && !frc.robot.RobotContainer.BallLimelightT.limelightHasTarget()) frc.robot.RobotContainer.DrivetrainT.arcadeDrive(0, 0.5);
        //Track last known angle
        lastKnownPosition = angle;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    frc.robot.RobotContainer.DrivetrainT.arcadeDrive(0, 0);
    frc.robot.RobotContainer.DrivetrainT.allowDrive(true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return size > 120;
  }
}
