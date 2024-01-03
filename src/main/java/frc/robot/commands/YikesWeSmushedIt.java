// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Smushable;

public class YikesWeSmushedIt extends CommandBase {
  private Smushable m_smushable;
  // Detect limit switch smushing and set zero position
  public YikesWeSmushedIt(Smushable smushable) {
    m_smushable = smushable;
  }

  @Override
  public void initialize() {
    m_smushable.setOrigin();
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
