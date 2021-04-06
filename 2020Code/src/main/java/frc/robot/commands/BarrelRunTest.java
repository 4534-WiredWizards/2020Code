/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class BarrelRunTest extends SequentialCommandGroup {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  
    public BarrelRunTest() {
        addCommands(
                new DriveDistance(90), //move forward 7.5 feet
                new DriveArc(30, 340), //do a full circle clockwise
                new DriveDistance(90), //move forward 90 inches
                new DriveArc(40, -315), //rotate 315 degrees counterclockwise
                new DriveDistance(85), //move forward 85 inches
                new DriveArc(22.5, -225), //rotate 225 degrees counterclockwise
                new DriveDistance(240) //move forward 240 feet
            ); 
        }   
    }