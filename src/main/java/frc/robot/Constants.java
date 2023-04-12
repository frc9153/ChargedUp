// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {
  public static class Control {
    public static final int driverControllerPort = 0;
    public static final int operatorControllerPort = 1;

    public static final int moveAxis = 1;
    public static final int rotateAxis = 0;
    public static final int shoulderAxis = 5;
    public static final int extruderinatorAxis = 1;
    // public static final int clawAxis = 5;
    /*
     * public static final int clawConeButton = 1;
     * public static final int clawCubeButton = 2;
     * public static final int clawOpenButton = 4;
     */
    public static final int clawManualOpenButton = 2;
    public static final int clawManualCloseButton = 3;
    public static final int extruderStoreButton = 2;
    public static final int extruderInButton = 1;
    public static final int extruderHalfButton = 3;
    public static final int extruderOutButton = 4;
  }

  public static class Autonomous {
    public static final double sleepingSpeedForward = 0.35;
    public static final double sleepingRotation = 0;
    public static final double sleepingDuration = 5.0;
    public static final double sleepingDurationBalance = 3.0;
    public static final double sleepingSpeedBalance = 0.5;
  }

  public static class Drivetrain {
    public static final int leftFrontMotorID = 2;
    public static final int leftBackMotorID = 3;
    public static final int rightFrontMotorID = 4;
    public static final int rightBackMotorID = 1;

    public static final double manualControlMultiplier = 1;//0.7
  }

  public static class Claw {
    public static final int clawMotorID = 7;

    public static final double clawP = 10.0;
    public static final double clawI = 0.0;
    public static final double clawD = 0.0;
    public static final double clawIZone = 0.0;
    public static final double clawFF = 0.0;

    public static final double minClawSpeed = -3.0;
    public static final double maxClawSpeed = 3.0;

    public static final double coneClawSetPoint = 0.0; // TODO: Dummy value
    public static final double cubeClawSetPoint = 0.1; // TODO: Dummy value
    public static final double openClawSetPoint = -1.0; // TODO: Dummy value

    public static final double clawPIDEpsilon = 0.001;

    public static final double manualCloseSpeed = 1;
    public static final double manualStopSpeed = 0;
    public static final double manualOpenSpeed = -1;

    public static final double clawTime = 1.5;
  }

  public static class Shoulder {
    /*
     * TODO: These PID-based motor systems are suuuper similar.
     * Is there a way to make these generic and only specify changes
     * to the default? Inheritance?
     */
    public static final int shoulderMotorID = 5;
    public static final int encoderDIOChannel = 0;
    public static final double encoderDistancePerRotation = 4.0;

    public static final double shoulderP = 10.0;
    public static final double shoulderI = 0.0;
    public static final double shoulderD = 0.0;
    public static final double shoulderIZone = 0.0;
    public static final double shoulderFF = 0.0;

    public static final double shoulderBrakeFactor = 0.17;

    public static final double minShoulderSpeed = -0.3;
    public static final double maxShoulderSpeed = 0.3;

    public static final double storeShoulderSetPoint = 0.28;
    public static final double floorShoulderSetPoint = 0.305;
    public static final double halfShoulderSetPoint = 0.5;
    public static final double upShoulderSetPoint = 0.56;

    public static final double shoulderPIDEpsilon = 0.05;
  }

  public static class Extruderinator {
    public static final int extruderMotorID = 6;

    public static final double extruderP = 10.0;
    public static final double extruderI = 0.0;
    public static final double extruderD = 0.0;
    public static final double extruderIZone = 0.0;
    
    public static final double extruderFF = 0.0;

    public static final double minExtruderSpeed = -0.5;
    public static final double maxExtruderSpeed = 0.5;

    public static final double storeExtruderSetPoint = 0.0;
    public static final double inExtruderSetPoint = 13.0;
    public static final double halfExtruderSetPoint = 18;
    public static final double outExtruderSetPoint = 50;

    public static final double extruderPIDEpsilon = 0.01;

    public static final double manualControlMultiplier = 0.2;

    public static final double manualInSpeed = -1;
    public static final double manualStopSpeed = 0;
    public static final double manualOutSpeed = 1;
    public static final double autonomousSpeed = 0.5;
  }

  public static class EternalBalance {
    // How much we gotta tip before we realize and run the other way
    public static final double balanceAngleThreshold = 0.1;

    // Multiplier for normalized [-1, 1] direction to convert to speed
    // public static final double balanceCompensation = 0.1;

    // Applied to angle^pow to get power
    public static final double balancePow = 0.8;

    public static final double balanceClampMin = -0.5;
    public static final double balanceClampMax = 0.5;
  }
}
