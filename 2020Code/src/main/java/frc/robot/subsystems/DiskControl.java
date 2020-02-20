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
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants.DebugConstants;
// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class DiskControl extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    private Solenoid piston;

  public DiskControl() {
    piston = new Solenoid(30, 2);
    addChild("piston", piston);
  }

  @Override
  public void periodic() {
    if(DebugConstants.debugMode) {
      Color detectedColor = m_colorSensor.getColor();
      SmartDashboard.putNumber("Red", detectedColor.red);
      SmartDashboard.putNumber("Green", detectedColor.green);
      SmartDashboard.putNumber("Blue", detectedColor.blue);
      if (detectedColor.blue > detectedColor.green && detectedColor.green > detectedColor.red || detectedColor.blue > 0.27) SmartDashboard.putString("DetectedColor", "Blue");
      else if (detectedColor.green > detectedColor.blue && detectedColor.blue > detectedColor.red) SmartDashboard.putString("DetectedColor", "Green");
      else if (detectedColor.red > detectedColor.green && detectedColor.green > detectedColor.blue || detectedColor.green < 0.5) SmartDashboard.putString("DetectedColor", "Red");
      else if (detectedColor.green > detectedColor.red && detectedColor.red > detectedColor.blue) SmartDashboard.putString("DetectedColor", "Yellow");
    }
  }
  
  public Color senseColor(){
    return m_colorSensor.getColor();
  }
}
