package cz.cvut.fel.ear.carstatus.notifications.malfunctions;

import cz.cvut.fel.ear.carstatus.notifications.BaseDecorator;
import cz.cvut.fel.ear.carstatus.notifications.Notifier;

public class LowBatteryConditionDecorator extends BaseDecorator {
    public LowBatteryConditionDecorator(Notifier n) {
        super(n);
    }

    @Override
    public String sendMessage(String message) {
        return super.sendMessage(message + "<p color='red'>Low battery condition</p>");
    }
}
