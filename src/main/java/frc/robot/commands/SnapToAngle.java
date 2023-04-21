// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class SnapToAngle extends CommandBase {
  Drivetrain m_drivetrain;
  DoubleSupplier m_yawSupplier;
  double m_increment;
  double m_target;

  public SnapToAngle(Drivetrain drivetrain, DoubleSupplier yawSupplier, double increment) {
    m_drivetrain = drivetrain;
    m_yawSupplier = yawSupplier;
    m_increment = increment;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_target = m_yawSupplier.getAsDouble() + m_increment;
    SmartDashboard.putNumber("Rot Target", m_target);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double rotSpeed = Constants.AngleSnap.rotSpeed;

    double currentAngle = m_yawSupplier.getAsDouble();
    SmartDashboard.putNumber("Yaw", currentAngle);

    // The robot knows where it is because it knows where it isn't
    double error = currentAngle - m_target;
    SmartDashboard.putNumber("Yaw Target Error", error);

    // If the delta is negative, we need to rotate the opposite way!!!
    if (error < 0) rotSpeed *= -1;

    m_drivetrain.arcadeDrive(0.0, rotSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // STOP!!!!! STOPSTOPSTOP STOPPP!!!
    m_drivetrain.arcadeDrive(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double error = Math.abs(m_yawSupplier.getAsDouble() - m_target);
    return error < Constants.AngleSnap.errorTolerance;
  }
}
