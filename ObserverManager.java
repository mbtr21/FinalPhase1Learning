package utils;
import services.User;


public interface ObserverManager {
    void addObserver(User observer);
    void removeObserver(User observer);
    void sendMessage(String message);
}
