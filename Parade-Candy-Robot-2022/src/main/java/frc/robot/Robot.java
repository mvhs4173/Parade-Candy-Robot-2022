// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.SwerveDrive;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private XboxController joy = RobotContainer.driverJoy;

  private SwerveDrive swerveDrive = RobotContainer.swerveDrive;
  public static final String maxMotorSpeedPercentKey = "MaxMotorSpeedPercent";
  public static final String swivelSpeedExponentKey = "SwivelSpeedExponent";
  SwerveDrive.MAX_DRIVE_PERCENT_SPEED = 0.37;
  private static double SWIVEL_SPEED_EXPONENT = .5;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    if (!Preferences.containsKey(maxMotorSpeedPercentKey)) {
      Preferences.setDouble(maxMotorSpeedPercentKey, MAX_DRIVE_PERCENT_SPEED);
    }
    if (!Preferences.containsKey(swivelSpeedExponentKey)) {
      Preferences.setDouble(swivelSpeedExponentKey, SWIVEL_SPEED_EXPONENT);
    }
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    RobotContainer.ledStrip.turnAllOff(); // Shut all lights off when the robot is disabled
  }

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {

    RobotContainer.phCompressor.enableAnalog(100.0, 120.0);

    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    RobotContainer.ledStrip.start(); // Start constantly updating LEDs to the data the strip was last given
    RobotContainer.cannon.setIsExtended(false);
    RobotContainer.cmdRunLEDPatrioticPattern.schedule();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    if (joy.getPOV() == 0) {
      swerveDrive.resetFieldOrientation();
    }
    swerveDrive.driveFieldOriented(new Vector2d(-joy.getLeftX(), -joy.getLeftY()), -joy.getRightX());
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();

    swerveDrive.resetModules();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    RobotContainer.ledStrip.chaseTrail(3, 6, 126, 152); // Call the chaseTest method, giving it our team color
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
