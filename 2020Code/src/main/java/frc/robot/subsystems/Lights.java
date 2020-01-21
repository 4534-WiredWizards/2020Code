/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import java.lang.System;

public class Lights extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  AddressableLED m_led = new AddressableLED(9);
  AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(60);
  String pattern;
  int red;
  int green;
  int blue;
  int specialA;
  int specialB;
  public Lights() {
    m_led.setLength(m_ledBuffer.getLength());
    m_led.setData(m_ledBuffer);
    m_led.start();

  }

  @Override
  public void periodic() {
    switch(pattern) {
      case "solid":
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          m_ledBuffer.setRGB(i, red, green, blue);
        }
        break;
      case "simple":
        for (var i = 0; i < m_ledBuffer.getLength(); i = i + specialA) {
          for (var j = i; j < i + specialB; j++) {
            m_ledBuffer.setRGB(j, red, green, blue);
          }
        }
        break;
      case "blink":
        if(System.currentTimeMillis() % (1000 *(specialA + specialB)) < 1000 *specialA) {
          for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            m_ledBuffer.setRGB(i, red, green, blue);
          }
        }
        else {
          for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            m_ledBuffer.setRGB(i, 0, 0, 0);
          }
        }
        break;
    }
    m_led.setData(m_ledBuffer);
  }
  public void setLedPattern(String Pattern, int RedValue, int GreenValue, int BlueValue, int ExtraOne, int ExtraTwo) {
    pattern = Pattern;
    red = RedValue;
    blue = BlueValue;
    green = GreenValue;
    specialA = ExtraOne;
    specialB = ExtraTwo;
  }
}
