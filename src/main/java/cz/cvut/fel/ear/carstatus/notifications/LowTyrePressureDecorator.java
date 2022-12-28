package cz.cvut.fel.ear.carstatus.notifications;

public class LowTyrePressureDecorator extends BaseDecorator {

    public LowTyrePressureDecorator(Notifier n) {
        super(n);
    }

    public void sendMessage(String message)
    {
        super.sendMessage(message + " low tyre pressure notifies");
    }
}
