/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  VictorSPX Shoot1 = new VictorSPX(17);
  VictorSPX Shoot2 = new VictorSPX(18);
  CANSparkMax Turret = new CANSparkMax(19, MotorType.kBrushless);
  CANEncoder TurretEncoder = Turret.getEncoder();
  public Shooter() {
    Shoot2.follow(Shoot1);
    Turret.setOpenLoopRampRate(5);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    setShooterSpeed(frc.robot.RobotContainer.m_joystick.getRawAxis(1));
    setTurretSpeed(frc.robot.RobotContainer.m_joystick.getRawAxis(0));
  }

  public void setShooterSpeed(double speed) {
    Shoot1.set(ControlMode.PercentOutput, speed * 0.75);
  }
  public void setTurretSpeed(double speed) {
    Turret.set(MathUtil.clamp(-speed, -0.1, 0.1));
  }
  public double getAngle() {
    return TurretEncoder.getPosition();
  }
}