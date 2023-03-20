// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Claw;

public class ClawManualControl extends CommandBase {
  private final Claw m_claw;
  private final DoubleSupplier m_extend;

  public ClawManualControl(Claw claw, DoubleSupplier extend) {
    m_claw = claw;
    m_extend = extend;

    addRequirements(m_claw);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double value = m_extend.getAsDouble();

    m_claw.setSpeed(value);
    SmartDashboard.putNumber("Claw Axis Rot", value);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return m_claw.isAtSetPoint();
  }
}