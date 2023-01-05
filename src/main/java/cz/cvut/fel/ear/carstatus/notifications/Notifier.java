package cz.cvut.fel.ear.carstatus.notifications;

public class Notifier {
    public String sendMessage(String message) {
        return "<p> </p><p>MALFUNCTIONS:</p>"+message;
    }
}
