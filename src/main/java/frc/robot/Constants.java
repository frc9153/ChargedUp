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

    public static final double closeClawSetPoint = 1.0; // TODO: Dummy value
    public static final double openClawSetPoint = 0.0; // TODO: Dummy value

    public static final double clawPIDEpsilon = 0.001;
  }

  public static class Shoulder {
    /* TODO: These PID-based motor systems are suuuper similar.
       Is there a way to make these generic and only specify changes
       to the default? Inheritance? */
    public static final int shoulderMotorID = 5;

    public static final double shoulderP = 10.0;
    public static final double shoulderI = 0.0;
    public static final double shoulderD = 0.0;
    public static final double shoulderIZone = 0.0;
    public static final double shoulderFF = 0.0;

    public static final double minShoulderSpeed = -1.0;
    public static final double maxShoulderSpeed = 1.0;

    public static final double downShoulderSetPoint = 0.0; // TODO: Dummy value
    public static final double upShoulderSetPoint = 1.0; // TODO: Dummy value

    public static final double shoulderPIDEpsilon = 0.001;
  }

  public static class Extruderinator {
    public static final int extruderMotorID = 5;

    public static final double extruderP = 10.0;
    public static final double extruderI = 0.0;
    public static final double extruderD = 0.0;
    public static final double extruderIZone = 0.0;
    public static final double extruderFF = 0.0;

    public static final double minExtruderSpeed = -1.0;
    public static final double maxExtruderSpeed = 1.0;

    public static final double inExtruderSetPoint = 0.0; // TODO: Dummy value
    public static final double outExtruderSetPoint = 1.0; // TODO: Dummy value

    public static final double extruderPIDEpsilon = 0.001;
  }

  public static class EternalBalance {
    // How much we gotta tip before we realize and run the other way
    public static final double balanceAngleThreshold = 0.1;
    public static final double balanceCompensation = 0.1;
  }
}
