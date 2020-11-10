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
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.Servo;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  WPI_VictorSPX Shoot1 = new WPI_VictorSPX(19);
  WPI_VictorSPX Shoot2 = new WPI_VictorSPX(18);
  Servo Hood = new Servo(7);
  Encoder ShootEncoder = new Encoder(8, 9, false, EncodingType.k4X);
  CANSparkMax Turret = new CANSparkMax(17, MotorType.kBrushless);
  CANEncoder TurretEncoder = Turret.getEncoder();
  PIDController pid = new PIDController(0.00007, 0, 0);
  double output = 0;
  double hoodSet = 0;
  double total = 0;
  int count = 0;
  double[] rollingAverage = {0,0,0,0,0};
  int pointer = 0;
  public Shooter() {
    Shoot2.follow(Shoot1);
    Shoot2.setInverted(false);
    Hood.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    // Turret.setOpenLoopRampRate(5);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (frc.robot.RobotContainer.m_joystick.getRawAxis(3) > 0.8 && hoodSet < 0.35){
      hoodSet += 0.005;
    }
    if (frc.robot.RobotContainer.m_joystick.getRawAxis(2) > 0.8 && hoodSet > 0){
      hoodSet -= 0.005;
    }
    SmartDashboard.putNumber("Hood value", hoodSet);
    Hood.setPosition(hoodSet);
    if(frc.robot.RobotContainer.m_joystick.getRawButton(5)){
      // rollingAverage[pointer] = ShootEncoder.getRate();
      // pointer = (pointer + 1) % 4;
      // double average = (rollingAverage[0] + rollingAverage[1] + rollingAverage[2] + rollingAverage[3] + rollingAverage[4]) / 5;
      // if(average > -450){
      //   output = 1;
      // }
      // else{
      //   output = 0.8;
      // }
      setShooterVoltage(9);
      // if(frc.robot.RobotContainer.m_joystick.getRawButton(3)){
      //   total = total + ShootEncoder.getRate();
      //   count++;
      //   SmartDashboard.putNumber("Shooter Average", total / count);
      // }
    }
    else {
      output = 0;
      setShooterVoltage(0);
    }
    SmartDashboard.putNumber("Shooter Output", output);
    setTurretSpeed(-frc.robot.RobotContainer.m_joystick.getRawAxis(0));
    SmartDashboard.putNumber("shooterencoder", ShootEncoder.getRate());
  }

  public void setShooterSpeed(double speed) {
    Shoot1.set(ControlMode.PercentOutput, speed * 1);
  }
  public void setTurretSpeed(double speed) {
    Turret.set(MathUtil.clamp(-speed * 0.1, -0.1, 0.1));
  }
  public double getAngle() {
    return TurretEncoder.getPosition();
  }
  public void setShooterVoltage(double volt){
    Shoot1.setVoltage(volt);
  }
}
