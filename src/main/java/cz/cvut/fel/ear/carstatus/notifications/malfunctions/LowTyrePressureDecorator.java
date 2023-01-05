package cz.cvut.fel.ear.carstatus.notifications.malfunctions;

import cz.cvut.fel.ear.carstatus.notifications.BaseDecorator;
import cz.cvut.fel.ear.carstatus.notifications.Notifier;

public class LowTyrePressureDecorator extends BaseDecorator {

    public LowTyrePressureDecorator(Notifier n) {
        super(n);
    }

    @Override
    public String sendMessage(String message) {
        return super.sendMessage(message + "<p>Low tyre pressure</p>");
    }
}
