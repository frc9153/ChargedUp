// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.ClawManualControl;
import frc.robot.commands.ClawControl;
import frc.robot.commands.DriveArcade;
import frc.robot.commands.ExtruderinatorControl;
import frc.robot.commands.ExtruderinatorManualControl;
import frc.robot.commands.ShoulderManualControl;
import frc.robot.commands.ShoulderControl;
import frc.robot.commands.EternalBalanceToggle;
import frc.robot.commands.YikesWeSmushedIt;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Extruderinator;
import frc.robot.subsystems.Shoulder;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
        // The robot's subsystems and commands are defined here...

        public final Drivetrain m_drivetrain = new Drivetrain();
        public final Claw m_claw = new Claw();
        public final Shoulder m_shoulder = new Shoulder();
        public final Extruderinator m_extruderinator = new Extruderinator();
        public final AHRS m_gyro = new AHRS(I2C.Port.kMXP);

        public final Command scoreAndMobility = Commands.sequence(
                new ShoulderControl(m_shoulder, Constants.Shoulder.upShoulderSetPoint),
                new ExtruderinatorControl(m_extruderinator, Constants.Extruderinator.outExtruderSetPoint),
                new ClawManualControl(m_claw, () -> Constants.Claw.manualOpenSpeed).withTimeout(2),
                Commands.parallel(new DriveArcade(m_drivetrain, () -> -Constants.Autonomous.sleepingSpeedForward, () -> -Constants.Autonomous.sleepingRotation)
                                .withTimeout(Constants.Autonomous.sleepingDuration),
                                new ExtruderinatorControl(m_extruderinator, Constants.Extruderinator.storeExtruderSetPoint)));
        
        public final Command scoreAndSit = Commands.sequence(
                new ShoulderControl(m_shoulder, Constants.Shoulder.upShoulderSetPoint),
                new ExtruderinatorControl(m_extruderinator, Constants.Extruderinator.outExtruderSetPoint),
                new ClawManualControl(m_claw, () -> Constants.Claw.manualOpenSpeed).withTimeout(2),
                new ExtruderinatorControl(m_extruderinator, Constants.Extruderinator.storeExtruderSetPoint));
        
        public final Command scoreAndBalance = Commands.sequence(
                new ShoulderControl(m_shoulder, Constants.Shoulder.upShoulderSetPoint),
                new ExtruderinatorControl(m_extruderinator, Constants.Extruderinator.outExtruderSetPoint),
                new ClawManualControl(m_claw, () -> Constants.Claw.manualOpenSpeed).withTimeout(2),
                Commands.parallel(new DriveArcade(m_drivetrain, () -> -Constants.Autonomous.sleepingSpeedBalance, () -> -Constants.Autonomous.sleepingRotation)
                                .withTimeout(Constants.Autonomous.sleepingDurationBalance),
                                new ExtruderinatorControl(m_extruderinator, Constants.Extruderinator.storeExtruderSetPoint), 
                                new EternalBalanceToggle(() -> m_gyro.getPitch(), m_drivetrain)));
        /*
         * public final Command asleepCommand = Commands.sequence(
         * Commands.parallel(new ExtruderinatorManualControl(m_extruderinator, () ->
         * Constants.Autonomous.sleepingExtruder))
         * );
         */

        /*
         * public final Command clawCloseCommand = Commands.parallel(
         * Commands.sequence(new WaitCommand(Constants.Claw.clawTime),
         * new ClawManualControl(
         * m_claw,
         * () -> 1,
         * () -> Constants.Autonomous.sleepingRotation)
         * .withTimeout(Constants.Autonomous.sleepingDuration),
         * new DriveArcade(
         * m_drivetrain,
         * () -> -Constants.Autonomous.sleepingSpeedForward,
         * () -> -Constants.Autonomous.sleepingRotation)
         * .withTimeout(Constants.Autonomous.sleepingDuration)));
         */

        private final CommandXboxController m_driverController = new CommandXboxController(
                        Constants.Control.driverControllerPort);

        private final CommandXboxController m_operatorController = new CommandXboxController(
                        Constants.Control.operatorControllerPort);

        private final XboxController m_driverControllerHID = m_driverController.getHID();
        private final XboxController m_operatorControllerHID = m_operatorController.getHID();

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                System.out.println("We're live folks");

                // Configure the trigger bindings
                configureBindings();

                // CameraServer.startAutomaticCapture(); // Cam 0
                // CameraServer.startAutomaticCapture(); // Cam 1
        }

        private void configureBindings() {
                /*
                 * The Driver Controller should have mostly automatic controls with as few
                 * buttons to worry about as possible. The Operator Controller should have
                 * manual controls for each output as a failsafe; the Operator Controller
                 * should not be used unless a sensor fails, something gets misaligned, etc.
                 */

                m_drivetrain.setDefaultCommand(new DriveArcade(
                                m_drivetrain,
                                () -> Math.pow(-m_driverController.getRawAxis(Constants.Control.moveAxis), 3.0)
                                                * Constants.Drivetrain.manualControlMultiplier,
                                () -> Math.pow(-m_driverController.getRawAxis(Constants.Control.rotateAxis), 3.0)
                                                * Constants.Drivetrain.manualControlMultiplier));
                /* - Polarity Swap - */
                // m_driverController.rightBumper().onTrue(new InstantCommand(() ->
                // m_drivePolarity = 1));
                // m_driverController.leftBumper().onTrue(new InstantCommand(() ->
                // m_drivePolarity = -1));

                /* - Claw - */
                m_operatorController.rightTrigger()
                                .onTrue(new ClawManualControl(m_claw, () -> Constants.Claw.manualOpenSpeed)); // Right
                                                                                                               //  -
                                                                                                               // Close
                m_operatorController.leftTrigger()
                                .onTrue(new ClawManualControl(m_claw, () -> Constants.Claw.manualCloseSpeed)); // Left
                                                                                                              //  -
                                                                                                              // Open

                m_operatorController.rightTrigger()
                                .onFalse(new ClawManualControl(m_claw, () -> Constants.Claw.manualStopSpeed)); // Right
                                                                                                               //  -
                                                                                                               // Close
                m_operatorController.leftTrigger()
                                .onFalse(new ClawManualControl(m_claw, () -> Constants.Claw.manualStopSpeed)); // Left
                                                                                                               //  -
                                                                                                               // Open

                /*
                 * m_driverController.button(Constants.Control.clawConeButton)
                 * .onTrue(new ClawControl(m_claw, Constants.Claw.coneClawSetPoint));
                 * m_driverController.button(Constants.Control.clawCubeButton)
                 * .onTrue(new ClawControl(m_claw, Constants.Claw.cubeClawSetPoint));
                 * m_driverController.button(Constants.Control.clawOpenButton)
                 * .onTrue(new ClawControl(m_claw, Constants.Claw.openClawSetPoint));
                 */
                /* - Shoulder - */
                // m_driverController.y().onTrue(new ShoulderControl(m_shoulder,
                // Constants.Shoulder.upShoulderSetPoint)); // Y -
                // // Shoulder
                // // UP!!!
                // m_driverController.b().onTrue(new ShoulderControl(m_shoulder,
                // Constants.Shoulder.downShoulderSetPoint)); // B -
                // // Shoulder
                // // DOWN!!!

                //0.30, 0.50
                m_shoulder.setDefaultCommand(new ShoulderManualControl( 
                                m_shoulder,
                                () -> -m_operatorController.getRawAxis(Constants.Control.shoulderAxis)
                                                + 0.25 + (0.2 * (m_extruderinator.getEncoder() / 40))));
                // + Constants.Shoulder.shoulderBrakeFactor));

                /*m_operatorController.povUp()
                                .onTrue();
                m_operatorController.povDown()
                                .onTrue();
                m_operatorController.povRight()
                                .onTrue();
                m_operatorController.povLeft()
                                .onTrue();*/

                /* - Extruderinator - */
                // Reset on limit switch
                final Trigger m_extruderinatorLimitSwitchTrigger = new Trigger(m_extruderinator::isSmushed);
                m_extruderinatorLimitSwitchTrigger.onTrue(new YikesWeSmushedIt(m_extruderinator));

                // m_extruderinator.setDefaultCommand(new ExtruderinatorManualControl(
                // m_extruderinator,
                // () ->
                // m_operatorController.getRawAxis(Constants.Control.extruderinatorAxis)));

                /// BEGIN

                m_extruderinator.setDefaultCommand(new ExtruderinatorManualControl(
                        m_extruderinator, 
                        () -> -m_operatorController.getRawAxis(Constants.Control.extruderinatorAxis)
                                        ));

                /*m_operatorController.leftTrigger().onTrue(new ExtruderinatorManualControl(
                                m_extruderinator,
                                () -> Constants.Extruderinator.manualInSpeed));
                m_operatorController.rightTrigger().onTrue(new ExtruderinatorManualControl(
                                m_extruderinator,
                                () -> Constants.Extruderinator.manualOutSpeed));

                m_operatorController.leftTrigger().onFalse(new ExtruderinatorManualControl(
                                m_extruderinator,
                                () -> Constants.Extruderinator.manualStopSpeed));
                m_operatorController.rightTrigger().onFalse(new ExtruderinatorManualControl(
                                m_extruderinator,
                                () -> Constants.Extruderinator.manualStopSpeed));*/

                //// END

                m_operatorController.button(Constants.Control.extruderStoreButton).onTrue(new ExtruderinatorControl(
                                m_extruderinator,
                                Constants.Extruderinator.storeExtruderSetPoint).withTimeout(1.0));// Button B -- Extruder Store
                m_operatorController.button(Constants.Control.extruderInButton).onTrue(Commands.parallel(new ExtruderinatorControl(
                                m_extruderinator,
                                Constants.Extruderinator.inExtruderSetPoint), new ShoulderControl(m_shoulder, Constants.Shoulder.floorShoulderSetPoint)).withTimeout(1.5));// Button A -- Extruder In
                m_operatorController.button(Constants.Control.extruderHalfButton).onTrue(Commands.parallel(new ExtruderinatorControl(
                                m_extruderinator,
                                Constants.Extruderinator.halfExtruderSetPoint), new ShoulderControl(m_shoulder, Constants.Shoulder.halfShoulderSetPoint)).withTimeout(1.0));// Button X -- Extruder Half
                m_operatorController.button(Constants.Control.extruderOutButton).onTrue(Commands.parallel(Commands.sequence(new WaitCommand(0.5), new ExtruderinatorControl(
                                m_extruderinator,
                                Constants.Extruderinator.outExtruderSetPoint)), new ShoulderControl(m_shoulder, Constants.Shoulder.upShoulderSetPoint)).withTimeout(1.5));// Button Y -- Extruder Out

                /* - Eternal Balance */
                // m_driverController.povUp().onTrue(new EternalBalanceToggle(() ->
                // m_gyro.getPitch(), m_drivetrain));


        }

        /**
         * Use this to pass the autonomous command to the main {@link Robot} class.
         *
         * @return the command to run in autonomous
         */
        public Command getAutonomousCommandMobility() {
                System.out.println("RoboDoom sleeps and approaches...");
                return scoreAndMobility;
        }
        public Command getAutonomousCommandStay() {
                System.out.println("RoboDoom sleeps and stares...");
                return scoreAndSit;
        }
        public Command getAutonomousCommandBalance() {
                System.out.println("RoboDoom sleeps and balances...");
                return scoreAndBalance;
        }
}
