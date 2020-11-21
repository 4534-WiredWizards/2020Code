package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class CodeSim {
    public CodeSim () {
         execute(); {
            double speed;
            double rotation;
            //Get joystick positions and set speed and rotation to them
            //Inputs below the inner bound are ignored
            double innerBound = 0.05;
			speed = Math.abs(frc.robot.RobotContainer.m_driverController.getRawAxis(1)) >= innerBound ? frc.robot.RobotContainer.m_driverController.getRawAxis(1) : 0;
            rotation = Math.abs(frc.robot.RobotContainer.m_driverController.getRawAxis(4)) >= innerBound ? frc.robot.RobotContainer.m_driverController.getRawAxis(4) : 0;
            //Go into slow speed mode if left bumper is pressed, slow rotation mode if right bumper is pressed
            speed = frc.robot.RobotContainer.m_driverController.getBumper(Hand.kLeft) ? speed * 0.5 : speed;
            rotation = frc.robot.RobotContainer.m_driverController.getBumper(Hand.kRight) ? rotation * 0.3 : rotation * 0.5;
            if (Math.abs(speed) > 0.1) rotation = rotation * 1.2;
            //Descrease speed to 0.85 normal speed, add extra 0.15 from left trigger.
            if (speed < 0) speed = speed * 0.85 - 0.15 * frc.robot.RobotContainer.m_driverController.getRawAxis(2);
            else speed = speed * 0.85 + 0.15 * frc.robot.RobotContainer.m_driverController.getRawAxis(2);
            //Only move if allowed to.
            if (frc.robot.RobotContainer.DrivetrainT.isDrivingAllowed() == true) {
                if (Math.abs(frc.robot.RobotContainer.m_driverController.getTriggerAxis(Hand.kRight)) > 0.1) frc.robot.RobotContainer.DrivetrainT.arcadeDrive(speed, rotation);
                if (Math.abs(frc.robot.RobotContainer.m_driverController.getTriggerAxis(Hand.kRight)) < 0.1) frc.robot.RobotContainer.DrivetrainT.arcadeDrive(-speed, rotation);
            }
            
          }
    }

    private void execute() {
    }
}