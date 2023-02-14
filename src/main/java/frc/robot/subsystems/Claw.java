// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxAnalogSensor;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Claw extends SubsystemBase {
  private CANSparkMax m_clawMotor;
  private SparkMaxPIDController m_clawPidController;
  private SparkMaxAnalogSensor m_potentiometer;
  
  // Glorious hack because we can't read setpoint from motor controller
  private double m_setPoint;

  public Claw() {
    m_clawMotor = new CANSparkMax(Constants.Claw.clawMotorID, null);
    
    m_clawPidController = m_clawMotor.getPIDController();
    m_potentiometer = m_clawMotor.getAnalog(SparkMaxAnalogSensor.Mode.kAbsolute);

    // PID Config setup
    m_clawPidController.setP(Constants.Claw.clawP);
    m_clawPidController.setI(Constants.Claw.clawI);
    m_clawPidController.setD(Constants.Claw.clawD);
    m_clawPidController.setIZone(Constants.Claw.clawIZone);
    m_clawPidController.setFF(Constants.Claw.clawFF);
    m_clawPidController.setOutputRange(Constants.Claw.minClawSpeed, Constants.Claw.maxClawSpeed);
    m_clawPidController.setFeedbackDevice(m_potentiometer);

    m_clawMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    
    // Make config survive reboot (learn from others' mistakes!)
    m_clawMotor.burnFlash();
  }

  public void setSetPoint(double setPoint) {
    m_setPoint = setPoint;
    m_clawPidController.setReference(setPoint, ControlType.kPosition);
  }

  public void setSpeed(double speed) {
    m_clawMotor.set(speed);
  }

  public boolean isAtSetPoint() {
    return Math.abs(m_setPoint - m_potentiometer.getPosition()) <= Constants.Claw.clawPIDEpsilon;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
