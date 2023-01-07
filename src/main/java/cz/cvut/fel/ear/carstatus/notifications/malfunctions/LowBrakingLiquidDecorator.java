package cz.cvut.fel.ear.carstatus.notifications.malfunctions;

import cz.cvut.fel.ear.carstatus.notifications.BaseDecorator;
import cz.cvut.fel.ear.carstatus.notifications.Notifier;

public class LowBrakingLiquidDecorator extends BaseDecorator {
    public LowBrakingLiquidDecorator(Notifier n) {
        super(n);
    }

    @Override
    public String sendMessage(String message) {
        return super.sendMessage(message + "<p color='red'>Low braking liquid level</p>");
    }
}
