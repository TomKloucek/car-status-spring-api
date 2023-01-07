package cz.cvut.fel.ear.carstatus.notifications;

public class BaseDecorator extends Notifier {
    private final Notifier wrappe;

    public BaseDecorator(Notifier n)
    {
        this.wrappe = n;
    }

    @Override
    public String sendMessage(String message)
    {
        return wrappe.sendMessage(message);
    }
}
