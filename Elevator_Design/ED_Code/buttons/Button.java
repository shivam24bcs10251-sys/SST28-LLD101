package buttons;

public abstract class Button {
    private boolean isPressed;

    public Button() {
        this.isPressed = false;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void press() {
        this.isPressed = true;
    }

    public void reset() {
        this.isPressed = false;
    }
}
