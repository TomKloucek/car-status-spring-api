package cz.cvut.fel.ear.carstatus.notifications;

public class LowTyrePressureDecorator extends BaseDecorator {

    public LowTyrePressureDecorator(Notifier n) {
        super(n);
    }

    public String sendMessage(String message) {
        super.sendMessage(message + "Low tyre pressure\n");
        return message;
    }
}
