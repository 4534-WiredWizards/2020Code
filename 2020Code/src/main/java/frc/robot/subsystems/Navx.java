/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj.TimedRobot;

public class Navx extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  public AHRS ahrs;
  public Navx() {
    try {
      /***********************************************************************
       * navX-MXP:
       * - Communication via RoboRIO MXP (SPI, I2C, TTL UART) and USB.            
       * - See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
       * 
       * navX-Micro:
       * - Communication via I2C (RoboRIO MXP or Onboard) and USB.
       * - See http://navx-micro.kauailabs.com/guidance/selecting-an-interface.
       * 
       * Multiple navX-model devices on a single robot are supported.
       ************************************************************************/
      //ahrs = new AHRS(SerialPort.Port.kUSB1);
      ahrs = new AHRS(SerialPort.Port.kMXP, SerialDataType.kProcessedData, (byte)50);                                                                                                                                                                                                                            
      ahrs.enableLogging(true);
    } catch (RuntimeException ex ) {
    DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
    }
    Timer.delay(1.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  public double getHeading() {
    return ahrs.getAngle();
  }
}
