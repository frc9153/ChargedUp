// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {
  public static class Control {
    public static final int driverControllerPort = 0;
    public static final int operatorControllerPort = 0;

    public static final int moveAxis = 1;
    public static final int rotateAxis = 0;
  }

  public static class Drivetrain {
    public static final int leftFrontMotorID = 4;
    public static final int leftBackMotorID = 3;
    public static final int rightFrontMotorID = 1;
    public static final int rightBackMotorID = 2;
  }

  public static class Claw {
    public static final int clawMotorID = 5;

    public static final double clawP = 10.0;
    public static final double clawI = 0.0;
    public static final double clawD = 0.0;
    public static final double clawIZone = 0.0;
    public static final double clawFF = 0.0;

    public static final double minClawSpeed = -1.0;
    public static final double maxClawSpeed = 1.0;

    public static final double closeClawSetPoint = 1.0;
    public static final double openClawSetPoint = 0.0;

    public static final double clawPIDEpsilon = 0.001;
  }
}
