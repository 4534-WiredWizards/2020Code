/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Solenoid;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Intake extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  private CANSparkMax motor;
  private Solenoid piston;
  public Intake() {
    piston = new Solenoid(30, 1);
    addChild("piston", piston);
    motor = new CANSparkMax(16, MotorType.kBrushless);
  }

  @Override
  public void periodic() {
    if(frc.robot.RobotContainer.m_joystick.getRawButton(2)) {
      motor.set(-0.6);
    }
    else {
      if(frc.robot.RobotContainer.m_joystick.getRawButton(3)) {
        motor.set(0.4);
      }
      else {
        motor.set(0);
      }
    }
  }
}
