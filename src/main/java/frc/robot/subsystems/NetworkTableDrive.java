// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class NetworkTableDrive extends SubsystemBase {
  final DoubleSubscriber moveSub;
  double moveOld;
  final DoubleSubscriber rotSub;
  double rotOld;
  final DoubleSubscriber badump;
  double oldBeat;
  Drivetrain m_drivetrain;
  double beatDelay;
  boolean pause = false;

  public NetworkTableDrive(Drivetrain drivetrain) {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("control");

    moveSub = table.getDoubleTopic("move").subscribe(0.0);
    rotSub = table.getDoubleTopic("rot").subscribe(0.0);
    badump = table.getDoubleTopic("badump").subscribe(0.0);
    m_drivetrain = drivetrain;

    beatDelay = System.currentTimeMillis();
  }

  @Override
  public void periodic() {
    double move = moveSub.get();
    double rot = rotSub.get();
    double beat = badump.get();

    double delta = System.currentTimeMillis() - beatDelay;
    if (beat != oldBeat) {
      oldBeat = beat;
      beatDelay = System.currentTimeMillis();
      pause = false;
      System.out.println("I am FREE");
    } else if (delta > 200) {
      m_drivetrain.arcadeDrive(0.0, 0.0);
      pause = true;
      System.out.println("I am CAGED");
      System.out.println(delta);
    }

    if (pause) {
      return;
    }

    if (move > 1)
      move = 1;
    if (move < -1)
      move = -1;
    if (rot > 1)
      move = 1;
    if (rot < -1)
      move = -1;

    move *= 0.2;
    rot *= 0.2;

    if (move != moveOld) {
      moveOld = move;
      m_drivetrain.arcadeDrive(move * 0.1, rot * 0.1);
      System.out.println("WE MOVE " + move);
    }
    if (rot != rotOld) {
      rotOld = rot;
      m_drivetrain.arcadeDrive(move * 0.1, rot * 0.1);
      System.out.println("WE ROTATE " + rot);
    }
  }
}