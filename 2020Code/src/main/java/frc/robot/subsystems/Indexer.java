/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.*;

public class Indexer extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  TalonSRX indexMotor = new TalonSRX(20);
  public Indexer() {
    

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(frc.robot.RobotContainer.m_joystick.getRawButton(1)) {
      indexMotor.set(ControlMode.PercentOutput, 0.8);
    }
    else if(frc.robot.RobotContainer.m_joystick.getRawButton(8)){
      indexMotor.set(ControlMode.PercentOutput, -0.6);
    }
    else {
      indexMotor.set(ControlMode.PercentOutput, 0);
    }
  }
}
