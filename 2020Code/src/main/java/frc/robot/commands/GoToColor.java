/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.lang.reflect.Array;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An example command that uses an example subsystem.
 */
public class GoToColor extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  SendableChooser<Integer> colorChooser = new SendableChooser<Integer>();
  int detected = -1; 
  String map[][] = {{"CW","CCW","CW","CW"},{"CW","CW","CCW","CW"},{"CW","CW","CW","CCW"},{"CCW","CW","CW","CW"}};
  /**
   * Creates a new ExampleCommand.
   *
   * @param
   *  The subsystem used by this command.
   */
  public GoToColor() {
    addRequirements(frc.robot.RobotContainer.Diskcontrol);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    colorChooser.addDefault("Blue", 0);
		colorChooser.addObject("Green", 1);
		colorChooser.addObject("Red", 2);
    colorChooser.addObject("Yellow", 3);
    SmartDashboard.putData("Color Chooser", colorChooser);
  }
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (frc.robot.RobotContainer.Diskcontrol.senseColor().blue > frc.robot.RobotContainer.Diskcontrol.senseColor().green && frc.robot.RobotContainer.Diskcontrol.senseColor().green > frc.robot.RobotContainer.Diskcontrol.senseColor().red || frc.robot.RobotContainer.Diskcontrol.senseColor().blue > 0.27) detected = 2;
    else if (frc.robot.RobotContainer.Diskcontrol.senseColor().green > frc.robot.RobotContainer.Diskcontrol.senseColor().blue && frc.robot.RobotContainer.Diskcontrol.senseColor().blue > frc.robot.RobotContainer.Diskcontrol.senseColor().red) detected = 3;
    else if (frc.robot.RobotContainer.Diskcontrol.senseColor().red > frc.robot.RobotContainer.Diskcontrol.senseColor().green && frc.robot.RobotContainer.Diskcontrol.senseColor().green > frc.robot.RobotContainer.Diskcontrol.senseColor().blue || frc.robot.RobotContainer.Diskcontrol.senseColor().green < 0.5) detected = 0;
    else if (frc.robot.RobotContainer.Diskcontrol.senseColor().green > frc.robot.RobotContainer.Diskcontrol.senseColor().red && frc.robot.RobotContainer.Diskcontrol.senseColor().red > frc.robot.RobotContainer.Diskcontrol.senseColor().blue) detected = 1;
    // if (detected != -1 && detected != (int)colorChooser.getSelected()) {
    //   frc.robot.RobotContainer.Diskcontrol.DiskMotorController(.2);
    // }
    if (map[detected][(int)colorChooser.getSelected()].equals("CW")) {
      frc.robot.RobotContainer.Diskcontrol.DiskMotorController(.2);
    }
    else frc.robot.RobotContainer.Diskcontrol.DiskMotorController(-.2);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    frc.robot.RobotContainer.Diskcontrol.DiskMotorController(0);
      
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return detected == (int)colorChooser.getSelected();
  }
}
