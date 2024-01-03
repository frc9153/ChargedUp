// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Smushable extends SubsystemBase {
  public Smushable() {}

  public void setOrigin() {
    System.out.println("RAAGHHHHHH!!! OVERRIDE ME!!!! NOWWW!!!!");
  }

  public boolean isSmushed() {
    System.out.println("RAAGHHHHHH!!! OVERRIDE ME!!!! NOWWW!!!!");
    return false;
  }
}
