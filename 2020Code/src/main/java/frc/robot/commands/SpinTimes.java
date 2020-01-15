/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.subsystems.DiskControl;

/**
 * An example command that uses an example subsystem.
 */
public class SpinTimes extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  int detected = -1;
  int lastDetected = -2; 
  int colorChanges = 0;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public SpinTimes() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(frc.robot.RobotContainer.DiskControlT);


  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    frc.robot.RobotContainer.DiskControlT.DiskMotorController(0.25);
    if (frc.robot.RobotContainer.DiskControlT.senseColor().blue > frc.robot.RobotContainer.DiskControlT.senseColor().green && frc.robot.RobotContainer.DiskControlT.senseColor().green > frc.robot.RobotContainer.DiskControlT.senseColor().red || frc.robot.RobotContainer.DiskControlT.senseColor().blue > 0.27) detected =2;
    else if (frc.robot.RobotContainer.DiskControlT.senseColor().green > frc.robot.RobotContainer.DiskControlT.senseColor().blue && frc.robot.RobotContainer.DiskControlT.senseColor().blue > frc.robot.RobotContainer.DiskControlT.senseColor().red) detected = 3;
    else if (frc.robot.RobotContainer.DiskControlT.senseColor().red > frc.robot.RobotContainer.DiskControlT.senseColor().green && frc.robot.RobotContainer.DiskControlT.senseColor().green > frc.robot.RobotContainer.DiskControlT.senseColor().blue || frc.robot.RobotContainer.DiskControlT.senseColor().green < 0.5) detected = 0;
    else if (frc.robot.RobotContainer.DiskControlT.senseColor().green > frc.robot.RobotContainer.DiskControlT.senseColor().red && frc.robot.RobotContainer.DiskControlT.senseColor().red > frc.robot.RobotContainer.DiskControlT.senseColor().blue) detected = 1;
    if (lastDetected != detected) {
        colorChanges ++;
        lastDetected = detected;
    }
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    frc.robot.RobotContainer.DiskControlT.DiskMotorController(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return colorChanges > 26;
  }
}
