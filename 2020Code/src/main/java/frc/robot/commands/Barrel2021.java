package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Barrel2021 extends SequentialCommandGroup {
    public static final double DriveDist = 150;
    public static final double TurnAngle = -90;

    public Barrel2021() {
        new DriveDistance(150);
        new TurnAngle(90);
        new DriveArc(40, 60);
        
    }
}