/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj.GenericHID.Hand;
// import edu.wpi.first.wpilibj.Solenoid;
// import edu.wpi.first.wpilibj.Spark;
// import edu.wpi.first.wpilibj.SpeedController;

public class Drivetrain extends SubsystemBase {
  private CANSparkMax rightMaster;
  private CANEncoder rightMasterEncoder;
  private CANSparkMax rightFollower1;
  private CANEncoder rightFollowerEncoder1;
  private CANSparkMax rightFollower2;
  private CANEncoder rightFollowerEncoder2;
  
  private CANSparkMax leftMaster;
  private CANEncoder leftMasterEncoder;
  private CANSparkMax leftFollower1;
  private CANEncoder leftFollowerEncoder1;
  private CANSparkMax leftFollower2;
  private CANEncoder leftFollowerEncoder2;

  private DifferentialDrive diffDrive;

  double workingSpeed = 1;
  double demoSpeed = 0.5;
  boolean demoMode = false;
  protected double innerBound = 0.05;
  // Change motor speed by this variable every loop (typically 20ms)
  double stepSize = 0.1;
  double maxSpeed = workingSpeed;
  double lastSpeed = 0;
  //Arcade drive scaling
  double driveScale = 0;
  double rotationScale = 0;
  //Direct driving varibles
  boolean drivingEnabled = true;
  double encoderFactor = 268/182.1;
  
  /**
   * Creates a new ExampleSubsystem.
   */
  public Drivetrain() {
    rightMaster = new CANSparkMax(10, MotorType.kBrushless);
    rightMaster.setInverted(true);
    rightMasterEncoder = rightMaster.getEncoder();
    rightMasterEncoder.setPositionConversionFactor(encoderFactor);
    rightMasterEncoder.setVelocityConversionFactor(encoderFactor);
    rightMaster.setOpenLoopRampRate(0.1);

    rightFollower1 = new CANSparkMax(11, MotorType.kBrushless);
    rightFollower1.setInverted(true);
    rightFollowerEncoder1 = rightFollower1.getEncoder();
    rightFollowerEncoder1.setPositionConversionFactor(encoderFactor);
    rightFollowerEncoder1.setVelocityConversionFactor(encoderFactor);
    rightFollower1.setOpenLoopRampRate(0.1);

    rightFollower2 = new CANSparkMax(12, MotorType.kBrushless);
    rightFollower2.setInverted(true);
    rightFollowerEncoder2 = rightFollower2.getEncoder();
    rightFollowerEncoder2.setPositionConversionFactor(encoderFactor);
    rightFollowerEncoder2.setVelocityConversionFactor(encoderFactor);
    rightFollower2.setOpenLoopRampRate(0.1);

    leftMaster = new CANSparkMax(15, MotorType.kBrushless);
    leftMaster.setInverted(true);
    leftMasterEncoder = leftMaster.getEncoder();
    leftMasterEncoder.setPositionConversionFactor(encoderFactor);
    leftMasterEncoder.setVelocityConversionFactor(encoderFactor);
    leftMaster.setOpenLoopRampRate(0.1);
    
    leftFollower1 = new CANSparkMax(14, MotorType.kBrushless);
    leftFollower1.setInverted(true);
    leftFollowerEncoder1 = leftFollower1.getEncoder();
    leftFollowerEncoder1.setPositionConversionFactor(encoderFactor);
    leftFollowerEncoder1.setVelocityConversionFactor(encoderFactor);
    leftFollower1.setOpenLoopRampRate(0.1);

    leftFollower2 = new CANSparkMax(13, MotorType.kBrushless);
    leftFollower2.setInverted(true);
    leftFollowerEncoder2 = leftFollower2.getEncoder();
    leftFollowerEncoder2.setPositionConversionFactor(encoderFactor);
    leftFollowerEncoder2.setVelocityConversionFactor(encoderFactor);
    leftFollower2.setOpenLoopRampRate(0.1);
    

    leftFollower1.follow(leftMaster);
    leftFollower2.follow(leftMaster);
    rightFollower1.follow(rightMaster);
    rightFollower2.follow(rightMaster);
    
    diffDrive = new DifferentialDrive(leftMaster, rightMaster);
    addChild("DiffDrive", diffDrive);
    diffDrive.setSafetyEnabled(false);
    diffDrive.setExpiration(0.1);
    diffDrive.setMaxOutput(1.0);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Left Encoders", getLeftEncoders());
    SmartDashboard.putNumber("Right Encoders", getRightEncoders());
    SmartDashboard.putNumber("Avg Encoders", getEncoderAverage());
  }

  public void arcadeDrive(double speed, double rotation) {
    SmartDashboard.putNumber("Drive Speed", speed);
    SmartDashboard.putNumber("Rot Speed", rotation);
    diffDrive.arcadeDrive(speed*maxSpeed, rotation*maxSpeed, true);
    lastSpeed = speed;
  }

  public void arcadeDriveScaled(double speed, double rotation) {
    diffDrive.arcadeDrive((speed*maxSpeed) * (1 - driveScale) + driveScale, (rotation*maxSpeed)* (1 - rotationScale) + rotationScale, true);
    lastSpeed = speed;
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    diffDrive.tankDrive(leftSpeed, rightSpeed);
  }

  public void setDemoMode(boolean newDemoMode) {
    demoMode = newDemoMode;
    if (demoMode == true) {
        maxSpeed = demoSpeed;
    } else {
        maxSpeed = workingSpeed;
    }
    return;
  }

  public double setMaxSpeed() {
    return maxSpeed;
  }

  public double getLeftEncoders() {
    return ((leftMasterEncoder.getPosition() + leftFollowerEncoder1.getPosition() + leftFollowerEncoder2.getPosition())/3);
  }

  public double getRightEncoders() {
    return ((-rightMasterEncoder.getPosition() - rightFollowerEncoder1.getPosition() - rightFollowerEncoder2.getPosition())/3);
  }

  public void allowDrive(boolean allow) {
    drivingEnabled = allow;
  }

  public boolean isDrivingAllowed() {
    return drivingEnabled;
  }
  public double getEncoderAverage() {
    return (getRightEncoders() + getLeftEncoders() / 2);
  }
  public void resetEncoders() {
    leftFollowerEncoder2.setPosition(0);
    leftFollowerEncoder1.setPosition(0);
    leftMasterEncoder.setPosition(0);
    rightFollowerEncoder2.setPosition(0);
    rightFollowerEncoder1.setPosition(0);
    rightMasterEncoder.setPosition(0);
  }
  public double getVelocity() {
    return ((leftMasterEncoder.getVelocity() + rightMasterEncoder.getVelocity()) / 2);
  }
  public double getLeftVelocity() {
    return leftMasterEncoder.getVelocity();
  }
  public double getRightVelocity() {
    return rightMasterEncoder.getVelocity();
  }
}
