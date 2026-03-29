package buttons;

import enums.Direction;

public class HallButton extends Button {
    private Direction direction;
    private int floorNumber;

    public HallButton(Direction direction, int floorNumber) {
        this.direction = direction;
        this.floorNumber = floorNumber;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
