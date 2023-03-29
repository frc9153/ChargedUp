// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Extruderinator;
import frc.robot.subsystems.Shoulder;

public class SimpleAutonomous extends CommandBase {
  private Extruderinator m_extruderinator;
  private Shoulder m_shoulder;
  // Detect limit switch smushing and set zero position
  public SimpleAutonomous(Extruderinator extruderinator) {
    m_extruderinator = extruderinator;
  }

  @Override
  public void initialize() {
    m_extruderinator.setOrigin();
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return true;
  }
}