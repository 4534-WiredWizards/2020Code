/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  double hoodSet = 0;
  double[] rollingAverage = {0,0,0,0,0};
  int pointer = 0;
  public Shooter() {
    Shoot2.follow(Shoot1);
    Shoot2.setInverted(false);
    Hood.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
  }

  @Override
  public void periodic() {
    rollingAverage[pointer] = ShootEncoder.getRate();
    if(pointer < 4) pointer++;
    else pointer = 0;
    SmartDashboard.putNumber("Shooter AVG", getFlywheelSpeed());
    SmartDashboard.putNumber("Shooter Direct", ShootEncoder.getRate());
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
    Shoot2.setVoltage(volt);
    SmartDashboard.putNumber("Shooter Direct", ShootEncoder.getRate());
  }
  public void setHood(double value){
    Hood.set(value);
  }
  public double getHood(){
    return Hood.get();
  }
  public double getFlywheelSpeed(){
    double total = 0;
    for(int i = 0; i < 5; i++){
      total += rollingAverage[i];
    }
    total /= 5;
    return total;
  }
}