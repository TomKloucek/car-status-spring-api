package cz.cvut.fel.ear.carstatus.notifications;

public class BaseDecorator extends Notifier {
    private Notifier wrappe;

    public BaseDecorator(Notifier n)
    {
        this.wrappe = n;
    }

    public void sendMessage(String message)
    {
        wrappe.sendMessage(message);
    }
}
