package models;

import enums.Direction;

public class Request {
    private int sourceFloor;
    private int destinationFloor;
    private Direction direction;

    public Request(int sourceFloor, int destinationFloor, Direction direction) {
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        this.direction = direction;
    }

    public int getSourceFloor() { return sourceFloor; }
    public int getDestinationFloor() { return destinationFloor; }
    public Direction getDirection() { return direction; }
}
