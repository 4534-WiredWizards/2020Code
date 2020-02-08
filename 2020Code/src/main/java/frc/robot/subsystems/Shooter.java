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
  PIDController pid = new PIDController(0.00007, 0, 0);
  boolean passedTarget = false;
  double output = 0;
  double total = 0;
  int count = 0;
  double[] bepis;
  public Shooter() {
    Shoot2.follow(Shoot1);
    Shoot2.setInverted(false);
    //Turret.setOpenLoopRampRate(5);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    int target = -235;
    if(frc.robot.RobotContainer.m_joystick.getRawButton(2)){
      // SmartDashboard.putBoolean("truefalse", output > target / 450.0);
      // if(output > target / 450.0 && !passedTarget){
      //   output = Math.pow(target / -450.0, 1.3);
      // }
      // else{
      //   output -= pid.calculate(ShootEncoder.getRate(), target);
      // }
      // setShooterSpeed(output);
      // if(ShootEncoder.getRate() < target){
      //   passedTarget=true;
      // }
      setShooterSpeed(0.8);
      if(frc.robot.RobotContainer.m_joystick.getRawButton(3)){
        total = total + ShootEncoder.getRate();
        count++;
        SmartDashboard.putNumber("Shooter Average", total / count);
      }
    }
    else {
      output = 0;
      setShooterSpeed(0);
      passedTarget = false;
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
}