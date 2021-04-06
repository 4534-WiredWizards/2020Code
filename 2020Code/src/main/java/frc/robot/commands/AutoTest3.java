/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class AutoTest3 extends SequentialCommandGroup {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    public static final double DriveDistance = 65; 
  
    public AutoTest3() {
        addCommands(
           new DriveArc(35, -90), //90 degree circle counterclowise
           new TurnAngle(170), //170 degrees clockwise
           new DriveDistance(65), //drive forward 65 inches
           new DriveArc(40, -180), //180 degrees counterclockwise
           new DriveDistance(65), //drive forward 65 inches
           new TurnAngle(180), //Turn 180 degrees
           new DriveDistance(65), //drive forward 65 inches
           new DriveArc(35, -180), //180 degrees counterclockwise
           new DriveDistance(65), //drive forward 65 inches
           new TurnAngle(180), //Turn 180 degrees
           new DriveArc(35, -90) //90 degrees counterclockwise
            ); 
        }   
    }
