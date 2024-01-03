// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Extruderinator;

public class ExtruderinatorManualControl extends CommandBase {
  private final Extruderinator m_extruderinator;
  private final DoubleSupplier m_rotate;

  public ExtruderinatorManualControl(Extruderinator extruderinator, DoubleSupplier rotate) {
    m_extruderinator = extruderinator;
    m_rotate = rotate;

    addRequirements(m_extruderinator);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double value = m_rotate.getAsDouble() * Constants.Extruderinator.manualControlMultiplier;

    m_extruderinator.setSpeed(value);
    SmartDashboard.putNumber("Extruderinator Axis Rot", value);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return m_extruderinator.isAtSetPoint();
  }
}