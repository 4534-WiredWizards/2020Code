// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package frc.robot.commands;

// import frc.robot.subsystems.*;
// import edu.wpi.first.wpilibj2.command.CommandBase;
// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// /**
//  * An example command that uses an example subsystem.
//  */
// public class GoToColor extends CommandBase {
//   @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
//   SendableChooser<Integer> colorChooser = new SendableChooser<Integer>();
//   /**
//    * Creates a new ExampleCommand.
//    *
//    * @param
//    *  The subsystem used by this command.
//    */
//   public GoToColor(ExampleSubsystem subsystem) {
//     addRequirements(frc.robot.RobotContainer.Diskcontrol);
//   }

//   // Called when the command is initially scheduled.
//   @Override
//   public void initialize() {
//     colorChooser.addDefault("Blue", 0);
// 		colorChooser.addObject("Green", 1);
// 		colorChooser.addObject("Red", 2);
//     colorChooser.addObject("Yellow", 3);
//     SmartDashboard.putData("Color Chooser", colorChooser);
//   }
//   m_colorSensor.getColor().blue
//   m_colorSensor.getColor().green
//   m_colorSensor.getColor().red
//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {
//     if (detectedColor.blue > detectedColor.green && detectedColor.green > detectedColor.red || detectedColor.blue > 0.27) detected = 0;
//     else if (detectedColor.green > detectedColor.blue && detectedColor.blue > detectedColor.red) detected = 1;
//     else if (detectedColor.red > detectedColor.green && detectedColor.green > detectedColor.blue || detectedColor.green < 0.5) detected = 2;
//     else if (detectedColor.green > detectedColor.red && detectedColor.red > detectedColor.blue) detected = 3;
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
