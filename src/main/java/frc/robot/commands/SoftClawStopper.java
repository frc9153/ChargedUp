// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Claw;

public class SoftClawStopper extends CommandBase {
  // Delta tracking for soft limit detection
  private Double m_lastPos = null;
  private int m_smushDeltaCounter = 0;

  private Claw m_claw;

  public SoftClawStopper(Claw claw) {
    m_claw = claw;

    addRequirements(claw);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_smushDeltaCounter = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Walk inward
    m_claw.setSpeed(-0.5);

    double pos = m_claw.getPosition();

    if (m_lastPos == null) {
      System.out.println("Init, setting lastpos");
      m_lastPos = pos;
      return;
    }

    double delta = Math.abs(m_lastPos - pos);
    m_lastPos = pos;

    if (delta < Constants.Claw.clawSmushedDeltaThreshold) {
      m_smushDeltaCounter++;
      System.out.println("SMUSHING AHHH");
      System.out.println(delta);
    }

    SmartDashboard.putNumber("Claw Init Delta", delta);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_claw.setSpeed(0.0);
    m_claw.setOrigin();
    System.out.println("Eahh! I'm done!");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_smushDeltaCounter >= Constants.Claw.clawSmushDeltaCountThreshold;
  }
}
