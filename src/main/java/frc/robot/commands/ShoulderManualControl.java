// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shoulder;

public class ShoulderManualControl extends CommandBase {
  private final Shoulder m_shoulder;
  private final DoubleSupplier m_rotate;

  public ShoulderManualControl(Shoulder shoulder, DoubleSupplier rotate) {
    m_shoulder = shoulder;
    m_rotate = rotate;

    addRequirements(m_shoulder);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double value = m_rotate.getAsDouble() / 10.0;

    m_shoulder.setSpeed(value);
    SmartDashboard.putNumber("Shoulder Axis Rot", value);
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
