package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Servo;

public class Drivetrain extends SubsystemBase {
    private WPI_TalonSRX leftMasterJPL;
    private WPI_TalonSRX rightMasterJPL;
    private DifferentialDrive deltaD;
    private WPI_TalonSRX leftFollowerJPLT;
    private WPI_TalonSRX rightFollowerJPLT;
    private WPI_VictorSPX leftFollowerJPLV;
    private WPI_VictorSPX rightFollowerJPLV;
    private Servo hood;

    public Drivetrain() {
        leftMasterJPL = new WPI_TalonSRX(0);
        rightMasterJPL = new WPI_TalonSRX(1);
        deltaD = new DifferentialDrive(leftMasterJPL, rightMasterJPL);
        deltaD.setSafetyEnabled(true);
        deltaD.setExpiration(0.1);
        deltaD.setMaxOutput(1.0);
        leftFollowerJPLT = new WPI_TalonSRX(2);
        rightFollowerJPLT = new WPI_TalonSRX(3);
        leftFollowerJPLV = new WPI_VictorSPX(4);
        rightFollowerJPLV = new WPI_VictorSPX(5);
        leftFollowerJPLT.follow(leftMasterJPL);
        leftFollowerJPLV.follow(leftMasterJPL);
        rightFollowerJPLT.follow(rightMasterJPL);
        rightFollowerJPLV.follow(rightMasterJPL);
        leftMasterJPL.setInverted(false);
        rightMasterJPL.setInverted(false);
        leftFollowerJPLT.setInverted(InvertType.OpposeMaster);
        rightFollowerJPLT.setInverted(InvertType.OpposeMaster);
        leftFollowerJPLV.setInverted(InvertType.FollowMaster);
        rightFollowerJPLV.setInverted(InvertType.FollowMaster);
        leftMasterJPL.configOpenloopRamp(0.2, 0);
        rightMasterJPL.configOpenloopRamp(0.2, 0);
        leftMasterJPL.configPeakCurrentDuration(100, 10);
        rightMasterJPL.configPeakCurrentDuration(100, 10);
        leftMasterJPL.configContinuousCurrentLimit(27, 10);
        rightMasterJPL.configContinuousCurrentLimit(27, 10);
        hood = new Servo(0);
        hood.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    }

    @Override
    public void periodic() {
        hood.setPosition(frc.robot.RobotContainer.m_driverController.getRawAxis(3));
    }

    public void ArcadeDrive(double speed, double rotation) {
        deltaD.arcadeDrive(speed, rotation, true);
    }

    public void TankDrive(double leftSpeed, double rightSpeed) {
        deltaD.tankDrive(leftSpeed, rightSpeed);
    }
    public void resetMotorControllers(){
        leftMasterJPL.configFactoryDefault();
        rightMasterJPL.configFactoryDefault();
        leftFollowerJPLT.configFactoryDefault();
        rightFollowerJPLT.configFactoryDefault();
        leftFollowerJPLV.configFactoryDefault();
        rightFollowerJPLV.configFactoryDefault();
    }
}
