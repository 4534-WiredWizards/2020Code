/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class DiskControl extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
    TalonSRX diskWheel = new TalonSRX(16);
    private final I2C.Port i2cPort = I2C.Port.kOnboard;

    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

    SendableChooser<Integer> colorChooser = new SendableChooser<Integer>();

  public DiskControl() {
    diskWheel.set(ControlMode.PercentOutput, 0);
    colorChooser.addDefault("Blue", 0);
		colorChooser.addObject("Green", 1);
		colorChooser.addObject("Red", 2);
    colorChooser.addObject("Yellow", 3);
    SmartDashboard.putData("Color Chooser", colorChooser);
  }

  @Override
  public void periodic() {
    int detected = -1;
    Color detectedColor = m_colorSensor.getColor();
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    if (detectedColor.blue > detectedColor.green && detectedColor.green > detectedColor.red || detectedColor.blue > 0.27) SmartDashboard.putString("DetectedColor", "Blue");
    else if (detectedColor.green > detectedColor.blue && detectedColor.blue > detectedColor.red) SmartDashboard.putString("DetectedColor", "Green");
    else if (detectedColor.red > detectedColor.green && detectedColor.green > detectedColor.blue || detectedColor.green < 0.5) SmartDashboard.putString("DetectedColor", "Red");
    else if (detectedColor.green > detectedColor.red && detectedColor.red > detectedColor.blue) SmartDashboard.putString("DetectedColor", "Yellow");
    if (detectedColor.blue > detectedColor.green && detectedColor.green > detectedColor.red || detectedColor.blue > 0.27) detected = 0;
    else if (detectedColor.green > detectedColor.blue && detectedColor.blue > detectedColor.red) detected = 1;
    else if (detectedColor.red > detectedColor.green && detectedColor.green > detectedColor.blue || detectedColor.green < 0.5) detected = 2;
    else if (detectedColor.green > detectedColor.red && detectedColor.red > detectedColor.blue) detected = 3;
    if (detected != -1 && detected != (int)colorChooser.getSelected()) {
      diskWheel.set(ControlMode.PercentOutput, 0.2);
    }
    else diskWheel.set(ControlMode.PercentOutput, 0);
  }
}