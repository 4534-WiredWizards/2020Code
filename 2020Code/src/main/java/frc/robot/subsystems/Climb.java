/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Solenoid;

public class Climb extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  private Solenoid piston;
  public Climb() {
    piston = new Solenoid(30, 3);
    addChild("piston", piston);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
