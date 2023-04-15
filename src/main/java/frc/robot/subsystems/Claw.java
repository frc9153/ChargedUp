// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class Claw extends Smushable {
  private CANSparkMax m_motor;
  private SparkMaxPIDController m_PIDController;
  private SparkMaxLimitSwitch m_limitSwitch;
  private RelativeEncoder m_encoder;
  
  // Glorious hack because we can't read setpoint from motor controller
  private double m_setPoint;

  public Claw() {
    m_motor = new CANSparkMax(Constants.Claw.clawMotorID, MotorType.kBrushless);
    m_encoder = m_motor.getEncoder();

    m_limitSwitch = m_motor.getReverseLimitSwitch(Type.kNormallyOpen);
    m_limitSwitch.enableLimitSwitch(true);

    m_PIDController = m_motor.getPIDController();

    // PID Config setup
    m_PIDController.setP(Constants.Claw.clawP);
    m_PIDController.setI(Constants.Claw.clawI);
    m_PIDController.setD(Constants.Claw.clawD);
    m_PIDController.setIZone(Constants.Claw.clawIZone);
    m_PIDController.setFF(Constants.Claw.clawFF);
    m_PIDController.setOutputRange(Constants.Claw.minClawSpeed, Constants.Claw.maxClawSpeed);
    //m_clawPIDController.setFeedbackDevice(m_encoder);

    m_motor.setIdleMode(CANSparkMax.IdleMode.kBrake);

    // Make config survive reboot (learn from others' mistakes!)
    m_motor.burnFlash();
  }

  public void setSetPoint(double setPoint) {
    m_setPoint = setPoint;
    m_PIDController.setReference(setPoint, ControlType.kPosition);
  }

  public void setSpeed(double speed) {
    m_motor.set(speed);
  }

  public boolean isAtSetPoint() {
    return Math.abs(m_setPoint - m_encoder.getPosition()) <= Constants.Claw.clawPIDEpsilon;
  }

  @Override
  public boolean isSmushed() {
    return m_limitSwitch.isPressed();
  }

  @Override
  public void setOrigin() {
    m_encoder.setPosition(0.0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Claw Winch Position", m_encoder.getPosition());
    SmartDashboard.putBoolean("Claw Limit Switch", m_limitSwitch.isPressed());
  }
}
