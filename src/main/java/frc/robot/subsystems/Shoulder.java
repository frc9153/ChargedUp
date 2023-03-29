// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shoulder extends SubsystemBase {
  private CANSparkMax m_shoulderMotor;
  private SparkMaxPIDController m_shoulderPIDController;
  private SparkMaxAbsoluteEncoder m_encoder;

  private double m_setPoint;

  /** Creates a new Shoulder. */
  public Shoulder() {
    /* TODO: This code is nearly identical to the Claw subsystem
      code. Assuming this is the correct way this should be done,
      it would probably be nice to subclass this out to a
      "PIDControlledMotorInterface" class or something.
    */

    m_shoulderMotor = new CANSparkMax(Constants.Shoulder.shoulderMotorID, MotorType.kBrushless);

    m_shoulderPIDController = m_shoulderMotor.getPIDController();
    m_encoder = m_shoulderMotor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle);

    // PID Config setup
    m_shoulderPIDController.setP(Constants.Shoulder.shoulderP);
    m_shoulderPIDController.setI(Constants.Shoulder.shoulderI);
    m_shoulderPIDController.setD(Constants.Shoulder.shoulderD);
    m_shoulderPIDController.setIZone(Constants.Shoulder.shoulderIZone);
    m_shoulderPIDController.setFF(Constants.Shoulder.shoulderFF);
    m_shoulderPIDController.setOutputRange(Constants.Shoulder.minShoulderSpeed, Constants.Shoulder.maxShoulderSpeed);
    m_shoulderPIDController.setFeedbackDevice(m_encoder);

    m_shoulderMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    
    // Make config survive reboot (learn from others' mistakes!)
    m_shoulderMotor.burnFlash();
  }

  public void setSetPoint(double setPoint) {
    m_setPoint = setPoint;
    m_shoulderPIDController.setReference(setPoint, ControlType.kPosition);
  }

  public void setSpeed(double speed) {
    m_shoulderMotor.set(speed);
  }

  public boolean isAtSetPoint() {
    return Math.abs(m_setPoint - m_encoder.getPosition()) <= Constants.Shoulder.shoulderPIDEpsilon;
  }
  
  public double getEncoder() {
    return m_encoder.getPosition();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Shoulder Encoder Position", m_encoder.getPosition());
  }
}
