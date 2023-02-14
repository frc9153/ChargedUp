// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ClawControl;
import frc.robot.commands.DriveArcade;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  public final Drivetrain m_drivetrain = new Drivetrain();
  public final Claw m_claw = new Claw();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(Constants.Control.driverControllerPort);
  
  private final CommandXboxController m_operatorController =
      new CommandXboxController(Constants.Control.operatorControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    m_drivetrain.setDefaultCommand(new DriveArcade(
      m_drivetrain,
      () -> -m_driverController.getRawAxis(Constants.Control.moveAxis),
      () -> -m_driverController.getRawAxis(Constants.Control.rotateAxis)
    ));
  }

  private void configureBindings() {
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

    m_operatorController.x().onTrue(new ClawControl(m_claw, Constants.Claw.closeClawSetPoint)); // X - Close
    m_operatorController.a().onTrue(new ClawControl(m_claw, Constants.Claw.openClawSetPoint));  // A - Open
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return m_autoCommand;
  }
}
