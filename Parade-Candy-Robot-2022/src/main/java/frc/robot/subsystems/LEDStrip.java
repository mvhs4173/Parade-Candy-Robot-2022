package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDStrip {
    private AddressableLED ledStrip; // Object for the PWM-based LED strip
    private AddressableLEDBuffer ledBuffer; // Essentially an array that organizes data for each
                                            // individual LED. Argue this object into the .setData() method
                                            // inside the AddressableLED object to update the data.
                                            // (once .start() is called, LEDs update whenever data is changed)

    private int chaseIndex = 0;

    // CONSTRUCTOR
    public LEDStrip(int pwmPort, int length) {
        this.ledStrip = new AddressableLED(pwmPort);
        this.ledBuffer = new AddressableLEDBuffer(length);
        ledStrip.setLength(ledBuffer.getLength());
    }

    public void start() {
        ledStrip.start(); // Start automatically updating the led strip to whatever data was last given
    }

    /**
     * Set all lights in the strip to black (off)
     */
    public void turnAllOff() {
        // Loop through all individual lights...
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setRGB(i, 0, 0, 0); // Turn off by setting all color values to 0 (black)
        }
        ledStrip.setData(ledBuffer); // Give the led strip the data from the buffer
    }

    public void chaseTest(int r, int g, int b) {
        // Move chase index up by 1 (from what it was last time this method was called)
        chaseIndex++;
        // If the index is at the end of the strip, go to the beginning
        if (chaseIndex > ledBuffer.getLength()) {
            chaseIndex = 0;
        }
        // Loop through all individual lights in the strip...
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            if (i == chaseIndex) {
                ledBuffer.setRGB(i, r, g, b); // If this is the one we want on, turn it on to the color given
            } else {
                ledBuffer.setRGB(i, 0, 0, 0); // Otherwise, turn it off
            }
        }
        ledStrip.setData(ledBuffer); // Give the led strip the data from the buffer
    }
}
