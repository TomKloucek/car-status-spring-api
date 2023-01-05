package cz.cvut.fel.ear.carstatus.notifications.malfunctions;

import cz.cvut.fel.ear.carstatus.notifications.BaseDecorator;
import cz.cvut.fel.ear.carstatus.notifications.Notifier;

public class LowTyreConditionDecorator extends BaseDecorator {

    public LowTyreConditionDecorator(Notifier n) {
        super(n);
    }

    @Override
    public String sendMessage(String message) {
        return super.sendMessage(message + "<p>Low tyre condition</p>");
    }
}
