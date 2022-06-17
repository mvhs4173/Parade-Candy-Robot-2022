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

    public LEDStrip(int pwmPort, int length) {
        this.ledStrip = new AddressableLED(pwmPort);
        this.ledBuffer = new AddressableLEDBuffer(length);
        ledStrip.setLength(ledBuffer.getLength());
    }

    public void start() {
        ledStrip.start();
    }

    public void turnAllOff() {
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setRGB(i, 0, 0, 0);
        }
        ledStrip.setData(ledBuffer);
    }

    public void chaseTest(int r, int g, int b) {
        chaseIndex++;
        if (chaseIndex > ledBuffer.getLength()) {
            chaseIndex = 0;
        }
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            if (i == chaseIndex) {
                ledBuffer.setRGB(i, r, g, b);
            } else {
                ledBuffer.setRGB(i, 0, 0, 0);
            }
        }
        ledStrip.setData(ledBuffer);
    }
}
