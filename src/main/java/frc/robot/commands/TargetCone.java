// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;
import java.util.ArrayList;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class TargetCone extends CommandBase {
  private final Drivetrain m_drivetrain;
  private ArrayList<Double> past_tx = new ArrayList<Double>();

  /** Creates a new DriveArcade. */
  public TargetCone(Drivetrain drivetrain) {
    m_drivetrain = drivetrain;
    past_tx.add(0.0);
    past_tx.add(0.0);
    past_tx.add(0.0);
    past_tx.add(0.0);
    past_tx.add(0.0);

    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");

    // read values periodically
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);

    // tx is range -27, 27
    past_tx.remove(0);
    past_tx.add(tx.getDouble(0.0));
    double avg_tx = 0.0;
    for (var i = 0; i < past_tx.size(); i++) {
      avg_tx += past_tx.get(i) / past_tx.size();
    }

    SmartDashboard.putNumber("avg_tx", avg_tx);

    if (avg_tx > Constants.TargetCone.deadzone) {
      m_drivetrain.arcadeDrive(0.0, -Constants.TargetCone.rotateSpeed);
    } else if (avg_tx < -Constants.TargetCone.deadzone) {
      m_drivetrain.arcadeDrive(0.0, Constants.TargetCone.rotateSpeed);
    } else {
      m_drivetrain.arcadeDrive(0.0, 0.0);
    }

    // post to smart dashboard periodically
    // SmartDashboard.putNumber("LimelightX", x);
    // SmartDashboard.putNumber("LimelightY", y);
    // SmartDashboard.putNumber("LimelightArea", area);
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