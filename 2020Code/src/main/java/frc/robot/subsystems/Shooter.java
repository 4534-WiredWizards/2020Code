/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.controller.PIDController;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  VictorSPX Shoot1 = new VictorSPX(17);
  VictorSPX Shoot2 = new VictorSPX(18);
  Encoder ShootEncoder = new Encoder(8, 9, false, EncodingType.k4X);
  CANSparkMax Turret = new CANSparkMax(19, MotorType.kBrushless);
  CANEncoder TurretEncoder = Turret.getEncoder();
  PIDController pid = new PIDController(0.001, 0, 0);
  double output;
  public Shooter() {
    Shoot2.follow(Shoot1);
    Shoot2.setInverted(false);
    //Turret.setOpenLoopRampRate(5);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(frc.robot.RobotContainer.m_joystick.getRawButton(2)){
      if(output < 0.2){
        output = 0.2;
      }
      output -= pid.calculate(ShootEncoder.getRate(),-400);
      setShooterSpeed(output);
    }
    else {
      output = 0;
      setShooterSpeed(0);
    }
    setTurretSpeed(frc.robot.RobotContainer.m_joystick.getRawAxis(0));
    SmartDashboard.putNumber("shooterencoder", ShootEncoder.getRate());
  }

  public void setShooterSpeed(double speed) {
    Shoot1.set(ControlMode.PercentOutput, speed * 0.3);
  }
  public void setTurretSpeed(double speed) {
    Turret.set(MathUtil.clamp(-speed * 0.1, -0.1, 0.1));
  }
  public double getAngle() {
    return TurretEncoder.getPosition();
  }
}