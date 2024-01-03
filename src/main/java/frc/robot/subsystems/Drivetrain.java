// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class Drivetrain extends SubsystemBase {
  MotorControllerGroup m_leftMotors = new MotorControllerGroup(
      new CANSparkMax(Constants.Drivetrain.leftFrontMotorID, MotorType.kBrushless), // Left front
      new CANSparkMax(Constants.Drivetrain.leftBackMotorID, MotorType.kBrushless) // Left back
  );

  MotorControllerGroup m_rightMotors = new MotorControllerGroup(
      new CANSparkMax(Constants.Drivetrain.rightFrontMotorID, MotorType.kBrushless), // Right front
      new CANSparkMax(Constants.Drivetrain.rightBackMotorID, MotorType.kBrushless) // Right back
  );

  DifferentialDrive m_differentialDrive = new DifferentialDrive(m_leftMotors, m_rightMotors);
  SlewRateLimiter m_moveLimiter = new SlewRateLimiter(1.8);
  SlewRateLimiter m_rotateLimiter = new SlewRateLimiter(1.8);
  boolean m_juiceApplied = true;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    m_rightMotors.setInverted(true);
  }

  public void arcadeDrive(double moveSpeed, double rotateSpeed) {
    if (m_juiceApplied) {
      m_differentialDrive.arcadeDrive(m_moveLimiter.calculate(moveSpeed), m_rotateLimiter.calculate(rotateSpeed));
    } else {
      m_differentialDrive.arcadeDrive(
          moveSpeed * Constants.Drivetrain.noJuiceMultiplier,
          rotateSpeed * Constants.Drivetrain.noJuiceMultiplier);
    }
  }

  public void setJuiceApplied(boolean juiceApplied) {
    m_juiceApplied = juiceApplied;
    // printf not working :^(
    System.out.println("Juice" + String.valueOf(m_juiceApplied));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}