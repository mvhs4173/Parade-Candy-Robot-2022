// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.FlashLEDLaunchPattern;
import frc.robot.commands.LaunchCandy;
import frc.robot.commands.RunLEDPatrioticPattern;
import frc.robot.subsystems.CandyCannon;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.LEDStrip;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.SwerveModule;
import frc.robot.subsystems.TalonSRXMotorController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  // JOYSTICKS/CONTROLLERS
  public static XboxController driverJoy = new XboxController(RobotMap.driverJoy);

  // BUTTONS
  public static JoystickButton launchButton = new JoystickButton(driverJoy, RobotMap.launchButton);

  // MISCELLANEOUS
  public static PneumaticsControlModule pcm = new PneumaticsControlModule(RobotMap.pcm);
  public static LEDStrip ledStrip = new LEDStrip(RobotMap.ledStrip, 400);

  // MOTOR CONTROLLERS
  public static TalonSRXMotorController driveMotorFrontRight = new TalonSRXMotorController(RobotMap.driveMotorFrontRight),
                                        driveMotorFrontLeft = new TalonSRXMotorController(RobotMap.driveMotorFrontLeft),
                                        driveMotorBackLeft = new TalonSRXMotorController(RobotMap.driveMotorBackLeft),
                                        driveMotorBackRight = new TalonSRXMotorController(RobotMap.driveMotorBackRight),
                                        swivelMotorFrontRight = new TalonSRXMotorController(RobotMap.swivelMotorFrontRight),
                                        swivelMotorFrontLeft = new TalonSRXMotorController(RobotMap.swivelMotorFrontLeft),
                                        swivelMotorBackLeft = new TalonSRXMotorController(RobotMap.swivelMotorBackLeft),
                                        swivelMotorBackRight = new TalonSRXMotorController(RobotMap.swivelMotorBackRight);

  // SOLENOIDS
  public static DoubleSolenoid cannonDoubleSolenoid = new DoubleSolenoid(RobotMap.pcm, PneumaticsModuleType.CTREPCM, RobotMap.cannonForwardChannel, RobotMap.cannonReverseChannel);

  // SENSORS //
  public static AHRS navX = new AHRS(SPI.Port.kMXP); // Gets NavX device installed into SPI port (integrated onto RoboRio). Other options would be to use USB or I2C

  // SUBSYSTEMS
  public static SwerveModule swerveModuleFR = new SwerveModule(driveMotorFrontRight, swivelMotorFrontRight, Constants.FR_SWIVEL_ZERO_ANGLE, Constants.FR_DRIVE_MULTIPLIER, Constants.FR_LOCATION),
                             swerveModuleFL = new SwerveModule(driveMotorFrontLeft, swivelMotorFrontLeft, Constants.FL_SWIVEL_ZERO_ANGLE, Constants.FL_DRIVE_MULTIPLIER, Constants.FL_LOCATION),
                             swerveModuleBL = new SwerveModule(driveMotorBackLeft, swivelMotorBackLeft, Constants.BL_SWIVEL_ZERO_ANGLE, Constants.BL_DRIVE_MULTIPLIER, Constants.BL_LOCATION),
                             swerveModuleBR = new SwerveModule(driveMotorBackRight, swivelMotorBackRight, Constants.BR_SWIVEL_ZERO_ANGLE, Constants.BR_DRIVE_MULTIPLIER, Constants.BR_LOCATION);
  public static SwerveDrive swerveDrive = new SwerveDrive(navX, swerveModuleFR, swerveModuleFL, swerveModuleBL, swerveModuleBR);

  public static CandyCannon cannon = new CandyCannon(cannonDoubleSolenoid);

  // COMMANDS
  public static RunLEDPatrioticPattern cmdRunLEDPatrioticPattern = new RunLEDPatrioticPattern(ledStrip);
  public static FlashLEDLaunchPattern cmdFlashLEDLaunchPattern = new FlashLEDLaunchPattern(ledStrip);
  public static LaunchCandy cmdLaunchCandy = new LaunchCandy(cannon, cmdFlashLEDLaunchPattern, cmdRunLEDPatrioticPattern);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    launchButton.whenPressed(cmdLaunchCandy);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
