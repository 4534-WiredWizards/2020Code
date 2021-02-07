/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoTest extends SequentialCommandGroup {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  public static final double DriveDist = 150; //scale 106%
  public static final double TurnAngle = 90;
  

  public AutoTest() {
    addCommands(
      new DriveDistance(150), //move forward first step

      new TurnAngle(90), //turn right 90 degrees 

      new DriveDistance(50), //move forward 

      new TurnAngle(90), //turn right 90 degrees

      new DriveDistance(70), //drive forward

      new TurnAngle(90), //turn right 90 degrees

      new DriveDistance(70), //drive forward

      new TurnAngle(90), //turn right 90 degrees

      new DriveDistance(175), //move forward 

      new TurnAngle(-90), //turn left 90 degrees

      new DriveDistance(50), //drive forward

      new TurnAngle(-90), //turn left 90 degrees 

      new DriveDistance(50), //drive forward 

      new TurnAngle(-90), //turn left 90 degrees

      new DriveDistance(50), //move forward

      new TurnAngle(-90), //turn left 90 

      new DriveDistance(100), //move forward

      new TurnAngle(90), //turn right 90 

      new DriveDistance(50), //move forward

      new TurnAngle(-90), //turn left 90

      new DriveDistance(50), //move forward

      new TurnAngle(-90), //turn left 

      new DriveDistance(50), //move forward
      
      new TurnAngle(-90), //turn left

      new DriveDistance(250), //back to the start!

      new DriveArc(40, 60) //this is a lie 
    );
  }
}