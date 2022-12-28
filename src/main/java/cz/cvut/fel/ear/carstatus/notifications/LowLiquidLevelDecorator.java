package cz.cvut.fel.ear.carstatus.notifications;

public class LowLiquidLevelDecorator extends BaseDecorator {
    public LowLiquidLevelDecorator(Notifier n) {
        super(n);
    }
    public void sendMessage(String message)
    {
        super.sendMessage(message + " low liquid notifies");
    }
}
