// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shoulder;

public class ShoulderControl extends CommandBase {
  private final Shoulder m_shoulder;
  private final double m_targetPosition;

  public ShoulderControl(Shoulder shoulder, double targetPosition) {
    m_shoulder = shoulder;
    m_targetPosition = targetPosition;

    addRequirements(m_shoulder);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_shoulder.setSetPoint(m_targetPosition);
  }

  @Override
  public void end(boolean interrupted) {
    // TODO: Should we be doing anything special here with the PID loop?
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_shoulder.isAtSetPoint();
  }
}
