package cz.cvut.fel.ear.carstatus.notifications;

public class Notifier {
    public String sendMessage(String message) {
        return "<p> </p><h3>MALFUNCTIONS:</h3>"+message;
    }
}
