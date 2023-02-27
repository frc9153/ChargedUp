// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class EternalBalanceToggle extends CommandBase {
  private final DoubleSupplier m_angleSupplier;
  private final Drivetrain m_drivetrain;
  private boolean m_active = false;

  public EternalBalanceToggle(DoubleSupplier angleSupplier, Drivetrain drivetrain) {
    m_angleSupplier = angleSupplier;
    m_drivetrain = drivetrain;
  }

  @Override
  public void initialize() {
    m_active = !m_active;
  }

  @Override
  public void execute() {
    if (!m_active) {
      return;
    }

    final double angle = m_angleSupplier.getAsDouble();

    // Angle isn't substantial enough to move
    if (Math.abs(angle) - Constants.EternalBalance.balanceAngleThreshold < 0) {
      return;
    }

    final int sign = angle > 0 ? 1 : -1;

    m_drivetrain.arcadeDrive(
      // Forward if angle is positive, backward if angle is negative
      Constants.EternalBalance.balanceCompensation * sign,
      0.0
    );
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
