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
public class AutoIntake extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  boolean prevButton6;
  double m_intakeSpeed;
  double m_indexerSpeed;
  boolean m_piston;
  public AutoIntake(double intakeSpeed, double indexerSpeed, boolean piston) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(frc.robot.RobotContainer.IntakeT);
    addRequirements(frc.robot.RobotContainer.IndexerT);
    m_intakeSpeed = intakeSpeed;
    m_indexerSpeed = indexerSpeed;
    m_piston = piston;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    frc.robot.RobotContainer.IntakeT.setMotor(m_intakeSpeed);
    frc.robot.RobotContainer.IntakeT.setPiston(m_piston);
    if(m_intakeSpeed < 0 && frc.robot.RobotContainer.IndexerT.ballAtEnd()){
      frc.robot.RobotContainer.IndexerT.setMotor(-0.9);
    }
    else{
      frc.robot.RobotContainer.IndexerT.setMotor(m_indexerSpeed);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    frc.robot.RobotContainer.IndexerT.setMotor(0);
    frc.robot.RobotContainer.IntakeT.setMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
