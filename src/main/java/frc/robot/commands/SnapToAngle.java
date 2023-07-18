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

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // m_target = ((m_yawSupplier.getAsDouble() + m_increment + 180.0 + 360.0) % 360.0) - 180.0;
    m_target = m_yawSupplier.getAsDouble();
    double pos_increment = Math.abs(m_increment);

    if (m_increment < 0) {
      m_target = Math.floor(m_target / pos_increment) * pos_increment;
    } else {
      m_target = Math.ceil(m_target / pos_increment) * pos_increment;
    }

    // If we're going so so so smally, go bigger
    if (getRotationError() < Constants.AngleSnap.tooIttyBittyAngle) {
      System.out.println("Hello! We are too small");
      m_target += m_increment;
      m_target = ((m_target + 360.0 + 180.0) % 360.0) - 180.0;
    } else {
      System.out.println("Yeahhhh its big'");
      System.out.println(getRotationError());
    }
    
    SmartDashboard.putNumber("Rot Target", m_target);
    SmartDashboard.putNumber("Raw Crazy Target", m_yawSupplier.getAsDouble());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double rotSpeed = Constants.AngleSnap.rotSpeed;

    // Starts as [-180, 180], we mess with it
    double currentAngle = m_yawSupplier.getAsDouble();
    SmartDashboard.putNumber("Yaw", currentAngle);

    // If the delta is negative, we need to rotate the opposite way!!!
    if (m_increment > 0) rotSpeed *= -1;

    SmartDashboard.putNumber("Rot Delta", rotSpeed);
    m_drivetrain.arcadeDrive(0.0, rotSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // STOP!!!!! STOPSTOPSTOP STOPPP!!!
    m_drivetrain.arcadeDrive(0.0, 0.0);
    System.out.println("We have escaped!");
  }

  private double getRotationError() {
    return Math.abs(Math.abs(m_yawSupplier.getAsDouble()) - Math.abs(m_target));
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getRotationError() < Constants.AngleSnap.errorTolerance;
  }
}
