package observer;

public class SnakeAndLadderConsoleNotifier implements IObserver {
    @Override
    public void update(String msg) {
        System.out.println("[NOTIFICATION] " + msg);
    }
}