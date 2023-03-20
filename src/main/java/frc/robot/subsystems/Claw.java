// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Claw extends SubsystemBase {
  private TalonSRX m_clawMotor;
  
  // Glorious hack because we can't read setpoint from motor controller
  private double m_setPoint;

  public Claw() {
    m_clawMotor = new TalonSRX(Constants.Claw.clawMotorID);

    // Config
    m_clawMotor.config_kP(0, Constants.Claw.clawP);
    m_clawMotor.config_kI(0, Constants.Claw.clawI);
    m_clawMotor.config_kD(0, Constants.Claw.clawD);
    m_clawMotor.config_IntegralZone(0, Constants.Claw.clawIZone);
    
    // FIXME: This might be the wrong value
    m_clawMotor.config_kF(0, Constants.Claw.clawFF);

    // FIXME: This is also probably not the right value
    m_clawMotor.configAllowableClosedloopError(0, Constants.Claw.clawPIDEpsilon);

    // FIXME: 100 as guess value. We'll find out what that does
    m_clawMotor.configPeakOutputForward(Constants.Claw.maxClawSpeed, 100);
    m_clawMotor.configPeakOutputReverse(Constants.Claw.minClawSpeed, 100);

    m_clawMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog);
  }

  public void setSetPoint(double setPoint) {
    m_setPoint = setPoint;
    m_clawMotor.set(ControlMode.Position, m_setPoint);
  }

  public void setSpeed(double speed) {
    // TODO: Wrong controlmode for the job?
    m_clawMotor.set(ControlMode.PercentOutput, speed);
  }

  public boolean isAtSetPoint() {
    return Math.abs(m_setPoint - m_clawMotor.getSelectedSensorPosition()) <= Constants.Claw.clawPIDEpsilon;
  }

  @Override
  public void periodic() {}
}
