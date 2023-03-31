// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Extruderinator extends SubsystemBase {
  private CANSparkMax m_extruderMotor;
  private SparkMaxPIDController m_extruderPIDController;
  private SparkMaxLimitSwitch m_extruderLimitSwitch;
  private RelativeEncoder m_extruderEncoder;

  private double m_setPoint;

  public Extruderinator() {
    /* The Extruderinator is a bit of a mystery to me as of now.
     * Here's what I'm writing this code under the presumption of:
     * - We're using Spark Maxes (Maxs? Maxs'?) as motor controllers
     * - We're directly connecting the limit switch to motor controllers,
     *   and the `getReverseLimitSwitch` API works like I imagine it does
     * - The limit switch is `kNormallyOpen`
     */
    m_extruderMotor = new CANSparkMax(Constants.Extruderinator.extruderMotorID, MotorType.kBrushless);
    m_extruderMotor.setInverted(true);

    m_extruderEncoder = m_extruderMotor.getEncoder();

    // TODO: No clue what the type is. Replace it when found!
    m_extruderLimitSwitch = m_extruderMotor.getReverseLimitSwitch(Type.kNormallyOpen);
    m_extruderLimitSwitch.enableLimitSwitch(true);

    m_extruderPIDController = m_extruderMotor.getPIDController();

    // PID Config setup
    m_extruderPIDController.setP(Constants.Extruderinator.extruderP);
    m_extruderPIDController.setI(Constants.Extruderinator.extruderI);
    m_extruderPIDController.setD(Constants.Extruderinator.extruderD);
    m_extruderPIDController.setIZone(Constants.Extruderinator.extruderIZone);
    m_extruderPIDController.setFF(Constants.Extruderinator.extruderFF);
    m_extruderPIDController.setOutputRange(
      Constants.Extruderinator.minExtruderSpeed,
      Constants.Extruderinator.maxExtruderSpeed
    );

    m_extruderMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    
    // Make config survive reboot (learn from others' mistakes!)
    m_extruderMotor.burnFlash();
  }

  public void setSetPoint(double setPoint) {
    m_setPoint = setPoint;
    m_extruderPIDController.setReference(setPoint, ControlType.kPosition);
  }

  public void setSpeed(double speed) {
    m_extruderMotor.set(speed);
  }

  public void setOrigin() {
    m_extruderEncoder.setPosition(0.0);
  }

  public boolean isSmushed() {
    return m_extruderLimitSwitch.isPressed();
  }

  public boolean isAtSetPoint() {
    return Math.abs(m_setPoint - m_extruderEncoder.getPosition()) <= Constants.Extruderinator.extruderPIDEpsilon;
  }

  public double getEncoder() {
    return m_extruderEncoder.getPosition();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Extruderinator Encoder Position", m_extruderEncoder.getPosition());
    SmartDashboard.putBoolean("Reverse Limit Switch", m_extruderLimitSwitch.isPressed());
  }
}