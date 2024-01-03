// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    SmartDashboard.putBoolean("Balance Enabled", true);
  }

  @Override
  public void execute() {
    final double angle = m_angleSupplier.getAsDouble();

    // Angle isn't substantial enough to move
    // if (Math.abs(angle) - Constants.EternalBalance.balanceAngleThreshold < 0) {
    // return;
    // }

    SmartDashboard.putNumber("Balance Angle", angle);

    // Preserve sign with abs of value
    double power = angle / 12.0;
    power *= Math.abs(power);

    // Clamp power to [-0.5, 0.5]
    power = Math.max(-0.3, Math.min(0.3, power));

    SmartDashboard.putNumber("Balance Power", power);

    m_drivetrain.arcadeDrive(
        // Forward if angle is positive, backward if angle is negative
        power,
        0.0);
  }

  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0, 0);
    SmartDashboard.putBoolean("Balance Enabled", false);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
