package models;

import buttons.InternalButton;
import enums.ElevatorState;
import enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class ElevatorCar {
    private int id;
    private int currentFloor;
    private ElevatorState state;
    private Direction direction;
    private int weightCapacity;
    private int currentWeight;
    private List<InternalButton> internalButtons;
    private boolean doorOpen;

    public ElevatorCar(int id, int numFloors, int weightCapacity) {
        this.id = id;
        this.currentFloor = 0; // Starts at ground
        this.state = ElevatorState.IDLE;
        this.direction = Direction.NONE;
        this.weightCapacity = weightCapacity;
        this.currentWeight = 0;
        this.doorOpen = false;
        
        this.internalButtons = new ArrayList<>();
        for (int i = 0; i <= numFloors; i++) {
            this.internalButtons.add(new InternalButton(i));
        }
    }

    public int getId() { return id; }
    public int getCurrentFloor() { return currentFloor; }
    public void setCurrentFloor(int currentFloor) { this.currentFloor = currentFloor; }
    
    public ElevatorState getState() { return state; }
    public void setState(ElevatorState state) { this.state = state; }

    public Direction getDirection() { return direction; }
    public void setDirection(Direction direction) { this.direction = direction; }

    public int getWeightCapacity() { return weightCapacity; }
    public int getCurrentWeight() { return currentWeight; }
    public void addWeight(int weight) { this.currentWeight += weight; }
    public void removeWeight(int weight) { this.currentWeight = Math.max(0, this.currentWeight - weight); }

    public boolean isOverweight() {
        return this.currentWeight > this.weightCapacity;
    }

    public List<InternalButton> getInternalButtons() { return internalButtons; }
    
    public boolean isDoorOpen() { return doorOpen; }
    public void setDoorOpen(boolean doorOpen) { this.doorOpen = doorOpen; }

    public void pressButton(int destinationFloor) {
        if (destinationFloor >= 0 && destinationFloor < internalButtons.size()) {
            internalButtons.get(destinationFloor).press();
        }
    }
}
