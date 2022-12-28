package cz.cvut.fel.ear.carstatus.notifications;

public class DriverabilityMalfunctionDecorator extends BaseDecorator {
    public DriverabilityMalfunctionDecorator(Notifier n) {
        super(n);
    }

    public void sendMessage(String message) {
        super.sendMessage(message + " driverability malfunction notifies");
    }
}
