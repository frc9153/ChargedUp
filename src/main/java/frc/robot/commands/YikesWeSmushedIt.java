// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Extruderinator;

public class YikesWeSmushedIt extends CommandBase {
  private Extruderinator m_extruderinator;
  // Detect limit switch smushing and set zero position
  public YikesWeSmushedIt(Extruderinator extruderinator) {
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
