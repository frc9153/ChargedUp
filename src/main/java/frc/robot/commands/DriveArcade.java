// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveArcade extends CommandBase {
  private final Drivetrain m_drivetrain;
  private final DoubleSupplier m_move;
  private final DoubleSupplier m_rotate;

  /** Creates a new DriveArcade. */
  public DriveArcade(
    Drivetrain drivetrain,
    DoubleSupplier move,
    DoubleSupplier rotate 
  ) {
    m_drivetrain = drivetrain;
    m_move = move;
    m_rotate = rotate;

    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Tone down speed for STEM Day 3rd graders
    m_drivetrain.arcadeDrive(m_move.getAsDouble()/*0.4*/, m_rotate.getAsDouble()/*0.4*/);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}