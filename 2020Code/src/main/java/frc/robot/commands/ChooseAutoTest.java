/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ChooseAutoTest extends CommandBase {
        private final Command m_BarrelRunTest = new BarrelRunTest();
        private final Command m_SlalomPath = new AutoTest2();
        private final Command m_BouncePath = new AutoTest3();

        SendableChooser<Command> pathChooser = new SendableChooser<Command>();

    public ChooseAutoTest(){
        pathChooser.setDefaultOption("Barrel Run", m_BarrelRunTest);
        pathChooser.addOption("Slalom Path", m_SlalomPath);
        pathChooser.addOption("Bounce Path", m_BouncePath);
        SmartDashboard.putData(pathChooser);
        
    }
}



