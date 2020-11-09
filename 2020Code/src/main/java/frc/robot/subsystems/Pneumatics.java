/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.Compressor;

public class Pneumatics extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  private Compressor compressor;
  public Pneumatics() {
    compressor = new Compressor(0);
    addChild("Compressor",compressor);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setCompressor(boolean set){
    if(set)
    compressor.start();
    else
    compressor.stop();
  }
}
