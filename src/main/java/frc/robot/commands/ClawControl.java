// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Claw;

public class ClawControl extends CommandBase {
  private final Claw m_claw;
  private final double m_targetPosition;

  public ClawControl(Claw claw, double targetPosition) {
    m_claw = claw;
    m_targetPosition = targetPosition;

    // Kv priority type thingey to cancel other commands that use this
    addRequirements(m_claw);
  }

  @Override
  public void initialize() {
    m_claw.setSetPoint(m_targetPosition);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    System.out.println("We're done.");
    m_claw.setSetPoint(m_claw.getPosition());
  }

  @Override
  public boolean isFinished() {
    return m_claw.isAtSetPoint();
  }
}
