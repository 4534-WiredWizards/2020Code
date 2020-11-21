// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.CommandBase;

// /**
//  * An example command that uses an example subsystem.
//  */
// public class ControlIntake extends CommandBase {
//   @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

//   /**
//    * Creates a new ExampleCommand.
//    *
//    * @param subsystem The subsystem used by this command.
//    */
//   boolean prevButton6;
//   public ControlIntake() {
//     // Use addRequirements() here to declare subsystem dependencies.
//     addRequirements(frc.robot.RobotContainer.IntakeT);
//     addRequirements(frc.robot.RobotContainer.IndexerT);
//   }

//   // Called when the command is initially scheduled.
//   @Override
//   public void initialize() {
//   }

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {
//     if(frc.robot.RobotContainer.m_joystick.getRawButton(2)) {
//       frc.robot.RobotContainer.IntakeT.setMotor(-0.6);
//     }
//     else {
//       if(frc.robot.RobotContainer.m_joystick.getRawButton(3)) {
//         frc.robot.RobotContainer.IntakeT.setMotor(0.4);
//       }
//       else {
//         frc.robot.RobotContainer.IntakeT.setMotor(0);
//       }
//     }
//     //frc.robot.RobotContainer.IntakeT.setPiston(frc.robot.RobotContainer.m_joystick.getRawButton(6));
//     if(!frc.robot.RobotContainer.m_joystick.getRawButton(6)) prevButton6 = true;
//     if(frc.robot.RobotContainer.m_joystick.getRawButton(6) && prevButton6){
//       frc.robot.RobotContainer.IntakeT.setPiston(!frc.robot.RobotContainer.IntakeT.getPiston());
//       prevButton6 = false;
//     }
//     if(frc.robot.RobotContainer.m_joystick.getRawButton(1)) {
//       frc.robot.RobotContainer.IndexerT.setMotor(-0.8);
//     }
//     else if(frc.robot.RobotContainer.m_joystick.getRawButton(8)){
//       frc.robot.RobotContainer.IndexerT.setMotor(0.8);
//     }
//     else if(frc.robot.RobotContainer.m_joystick.getRawButton(2) &&frc.robot.RobotContainer.IndexerT.ballAtEnd()){
//       frc.robot.RobotContainer.IndexerT.setMotor(-0.9);
//     }
//     else{
//       frc.robot.RobotContainer.IndexerT.setMotor(0);
//     }
//   }

//   // Called once the command ends or is interrupted.
//   @Override
//   public void end(boolean interrupted) {
//   }

//   // Returns true when the command should end.
//   @Override
//   public boolean isFinished() {
//     return false;
//   }
// }
