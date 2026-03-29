package buttons;

public class InternalButton extends Button {
    private int destinationFloor;

    public InternalButton(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }
}
