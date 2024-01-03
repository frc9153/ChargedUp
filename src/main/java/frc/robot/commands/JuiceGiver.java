// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class JuiceGiver extends CommandBase {
  Drivetrain m_drivetrain;
  boolean m_juiceTarget;

  public JuiceGiver(Drivetrain drivetrain, boolean juiceTarget) {
    m_drivetrain = drivetrain;
    m_juiceTarget = juiceTarget;
  }

  @Override
  public void initialize() {
    m_drivetrain.setJuiceApplied(m_juiceTarget);
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
