// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LEDStrip;

public class RunPatrioticPattern extends CommandBase {
  LEDStrip ledStrip;
  Timer timer = new Timer();

  double delay = 0.1; // (seconds) How long to wait between each increment of the pattern

  /** Creates a new RunPatrioticPattern. */
  public RunPatrioticPattern(LEDStrip ledStrip) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.ledStrip = ledStrip;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ledStrip.setToPatrioticPattern();
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (timer.get() >= delay) {
      ledStrip.incrementPattern();
      timer.reset();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ledStrip.turnAllOff();
    timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
