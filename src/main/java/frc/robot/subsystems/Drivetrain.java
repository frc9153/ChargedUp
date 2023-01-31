// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class Drivetrain extends SubsystemBase {
  MotorControllerGroup leftMotors = new MotorControllerGroup(
    new CANSparkMax(4, MotorType.kBrushless), // Left front
    new CANSparkMax(3, MotorType.kBrushless) // Left back
  );
  
  MotorControllerGroup rightMotors = new MotorControllerGroup(
    new CANSparkMax(1, MotorType.kBrushless), // Right front
    new CANSparkMax(2, MotorType.kBrushless) // Right back
  );

  DifferentialDrive differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
  
  /** Creates a new Drivetrain. */
  public Drivetrain() {
    rightMotors.setInverted(true);
  }

  public void arcadeDrive(double moveSpeed, double rotateSpeed) {
    differentialDrive.arcadeDrive(moveSpeed, rotateSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}