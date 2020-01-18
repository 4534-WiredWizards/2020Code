/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;

public class BallLimelight extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  public BallLimelight() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  protected NetworkTable limeTable = NetworkTableInstance.getDefault().getTable("limelight");
    
    //Has target
    protected NetworkTableEntry tv = limeTable.getEntry("tv");
    //Horizontal Offset
    protected NetworkTableEntry tx = limeTable.getEntry("tx");
    //Vertical Offset
    protected NetworkTableEntry ty = limeTable.getEntry("ty");
    //Amount of 
    protected NetworkTableEntry ta = limeTable.getEntry("ta");
    //Skew
    protected NetworkTableEntry ts = limeTable.getEntry("ts");
    //Latency
    protected NetworkTableEntry tl = limeTable.getEntry("tl");
    //Short side of blue box
    protected NetworkTableEntry tshort = limeTable.getEntry("tshort");
    //Long side of blue box
    protected NetworkTableEntry tlong = limeTable.getEntry("tlong");
    //Horizontal of yellow box
    protected NetworkTableEntry thor = limeTable.getEntry("thor");
    //Vertical of yellow box
    protected NetworkTableEntry tvert = limeTable.getEntry("tvert");

    public void setLEDMode(double mode) {
        if (mode == 0 && getLEDMode() != 0) {
            limeTable.getEntry("ledMode").setNumber(0);
        } else if (mode == 1 && getLEDMode() != 1) {
            limeTable.getEntry("ledMode").setNumber(1);
        } else if (mode == 2 && getLEDMode() != 2) {
            limeTable.getEntry("ledMode").setNumber(2);
        } else if (mode == 3 && getLEDMode() != 3) {
            limeTable.getEntry("ledMode").setNumber(3);
        }
    }

    public double getLEDMode() {
		final double LEDMode = limeTable.getEntry("ledMode").getDouble(1);
		return LEDMode;
    }
    /*private void debugLimeLED() {
        System.out.println(limeTable.getEntry("ledMode").getDouble(1));
    }*/
    public double getXSkew() {
        return tx.getDouble(0);
    }
    public double getYSkew() {
        return ty.getDouble(0);
    }
    public boolean limelightHasTarget() {
        if(tv.getDouble(0) == 1.0) return true;
        else return false;
    }
    public double getAreaPercent() {
        return ta.getDouble(0);
    }
    public double getArea() {
        return (ta.getDouble(0) / 100) * 76800;
    }
    public double getLatency() {
        return tl.getDouble(0);
    }
    public double getBlueBoxLong() {
        return tlong.getDouble(0);
    }
    public double getBlueBoxShort() {
        return tshort.getDouble(0);
    }
    public double getYellowBoxHorizontal() {
        return thor.getDouble(0);
    }
    public double getYellowBoxVertical() {
        return tvert.getDouble(0);
    }
    public void setPipeline(int pipeline) {
        limeTable.getEntry("pipeline").setNumber(pipeline);
    }
}
