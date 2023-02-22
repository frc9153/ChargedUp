// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Extruderinator;

public class ExtruderinatorControl extends CommandBase {
  private final Extruderinator m_extruderinator;
  private final double m_targetPosition;

  public ExtruderinatorControl(Extruderinator extruderinator, double targetPosition) {
    m_extruderinator = extruderinator;
    m_targetPosition = targetPosition;

    addRequirements(m_extruderinator);
  }

  @Override
  public void initialize() {
    m_extruderinator.setSetPoint(m_targetPosition);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return m_extruderinator.isAtSetPoint();
  }
}
