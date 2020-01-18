/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  VictorSPX Shoot1 = new VictorSPX(17);
  VictorSPX Shoot2 = new VictorSPX(18);
  public Shooter() {
    Shoot2.follow(Shoot1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    setShooterSpeed(frc.robot.RobotContainer.m_joystick.getRawAxis(1));
  }

  public void setShooterSpeed(double speed) {
    Shoot1.set(ControlMode.PercentOutput, speed * 0.75);
  }
  public void setTurretSpeed(double speed) {
    Shoot1.set(ControlMode.PercentOutput, speed * 0.75);
  }
}