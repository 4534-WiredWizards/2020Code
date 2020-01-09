/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.GenericHID.Hand;

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
  
  /**
   * Creates a new ExampleSubsystem.
   */
  public Drivetrain() {
    rightMaster = new CANSparkMax(10, MotorType.kBrushless);
    rightMaster.setInverted(true);
    rightMasterEncoder = rightMaster.getEncoder();
    
    rightFollower1 = new CANSparkMax(11, MotorType.kBrushless);
    rightFollower1.setInverted(true);
    rightFollowerEncoder1 = rightFollower1.getEncoder();

    rightFollower2 = new CANSparkMax(12, MotorType.kBrushless);
    rightFollower2.setInverted(true);
    rightFollowerEncoder2 = rightFollower2.getEncoder();



    leftMaster = new CANSparkMax(13, MotorType.kBrushless);
    leftMaster.setInverted(true);
    leftMasterEncoder = leftMaster.getEncoder();
    
    leftFollower1 = new CANSparkMax(14, MotorType.kBrushless);
    leftFollower1.setInverted(true);
    leftFollowerEncoder1 = leftFollower1.getEncoder();

    leftFollower2 = new CANSparkMax(15, MotorType.kBrushless);
    leftFollower2.setInverted(true);
    leftFollowerEncoder2 = leftFollower2.getEncoder();
    

    leftFollower1.follow(leftMaster);
    leftFollower2.follow(leftMaster);
    rightFollower1.follow(rightMaster);
    rightFollower2.follow(rightMaster);
    
    diffDrive = new DifferentialDrive(leftMaster, rightMaster);
    addChild("DiffDrive", diffDrive);
    diffDrive.setSafetyEnabled(true);
    diffDrive.setExpiration(0.1);
    diffDrive.setMaxOutput(1.0);
    //setDefaultCommand(new DriveWithJoystick());
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Left Encoders", getLeftEncoders());
    SmartDashboard.putNumber("Right Encoders", getRightEncoders());

    // double speed;
    // double rotation;
    // //Get joystick positions and set speed and rotation to them
    // //Inputs below the inner bound are ignored
    // speed = Math.abs(frc.robot.RobotContainer.m_driverController.getRawAxis(1)) >= innerBound ? frc.robot.RobotContainer.m_driverController.getRawAxis(1) : 0;
    // rotation = Math.abs(frc.robot.RobotContainer.m_driverController.getRawAxis(4)) >= innerBound ? frc.robot.RobotContainer.m_driverController.getRawAxis(4) : 0;
    // //Go into slow speed mode if left bumper is pressed, slow rotation mode if right bumper is pressed
    // speed = frc.robot.RobotContainer.m_driverController.getBumper(Hand.kLeft) ? speed * 0.5 : speed;
    // rotation = frc.robot.RobotContainer.m_driverController.getBumper(Hand.kRight) ? rotation * 0.5 : rotation;
    // //Descrease speed to 0.85 normal speed, add extra 0.15 from left trigger.
    // if (speed < 0) speed = speed * 0.85 - 0.15 * frc.robot.RobotContainer.m_driverController.getRawAxis(2);
    // else speed = speed * 0.85 + 0.15 * frc.robot.RobotContainer.m_driverController.getRawAxis(2);
    // //Only move if allowed to.
    // if (frc.robot.RobotContainer.DriveTrain.isDrivingAllowed() == true) {
    //     frc.robot.RobotContainer.DriveTrain.arcadeDrive(speed, rotation);
    // }
  }

  public void arcadeDrive(double speed, double rotation) {
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
    return ((rightMasterEncoder.getPosition() + rightFollowerEncoder1.getPosition() + rightFollowerEncoder2.getPosition())/3);
  }

  public void allowDrive(boolean allow) {
    drivingEnabled = allow;
  }

  public boolean isDrivingAllowed() {
    return drivingEnabled;
  }
}
