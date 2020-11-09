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
  boolean prevButton6 = false;
  public Intake() {
    piston = new Solenoid(0, 1);
    addChild("piston", piston);
    motor = new CANSparkMax(16, MotorType.kBrushless);
  }

  @Override
  public void periodic() {
  }
  public void setMotor(double speed){
    motor.set(speed);
  }
  public void setPiston(boolean state){
    piston.set(state);
  }
  public boolean getPiston(){
    return piston.get();
  }
}
