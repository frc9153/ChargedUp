// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class EternalBalanceToggle extends CommandBase {
  private final DoubleSupplier m_angleSupplier;
  private final Drivetrain m_drivetrain;

  public EternalBalanceToggle(DoubleSupplier angleSupplier, Drivetrain drivetrain) {
    m_angleSupplier = angleSupplier;
    m_drivetrain = drivetrain;
  }

  @Override
  public void initialize() {
    // TODO: Toggle
  }

  @Override
  public void execute() {
    // TODO arcadeDrive based on pitch
    // m_drivetrain.arcadeDrive(m_move.getAsDouble(), m_rotate.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
