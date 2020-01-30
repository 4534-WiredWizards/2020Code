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
  AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(5);
  String pattern = "none";
  int red = 0;
  int green = 0;
  int blue = 0;
  int specialA = 0;
  int specialB = 0;
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
      case "rainbow road":
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          hsvToRgb((i % specialA)/specialA, 1, 1);
          m_ledBuffer.setRGB(i, red, green, blue);
        }
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
      default:
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
  // public void convertRainbow(int H){
  //   if (0 < H < 60)
  //   return (1,x,0);
  // }
  public void hsvToRgb(float hue, float saturation, float value) {

    int h = (int)(hue * 6);
    float f = hue * 6 - h;
    float p = value * (1 - saturation);
    float q = value * (1 - f * saturation);
    float t = value * (1 - (1 - f) * saturation);

    switch (h) {
      case 0: 
        rgbToInt(value, t, p);
        break;
      case 1: 
        rgbToInt(q, value, p);
        break;
      case 2:
        rgbToInt(p, value, t);
        break;
      case 3:
        rgbToInt(p, q, value);
        break;
      case 4:
        rgbToInt(t, p, value);
        break;
      case 5: 
        rgbToInt(value, p, q);
        break;
      default: throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
    }
  }

  public void rgbToInt(float r, float g, float b) {
    red = (int)(r * 255);
    blue = (int)(b * 255);
    green = (int)(g * 255);
  }
}

