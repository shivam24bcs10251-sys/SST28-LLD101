package controllers;

import models.ElevatorCar;
import models.Request;
import enums.ElevatorState;
import enums.Direction;
import strategies.RequestProcessingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class ElevatorController {
    private ElevatorCar elevatorCar;
    private RequestProcessingStrategy processingStrategy;
    private List<Request> requestQueue;

    public ElevatorController(ElevatorCar elevatorCar, RequestProcessingStrategy processingStrategy) {
        this.elevatorCar = elevatorCar;
        this.processingStrategy = processingStrategy;
        this.requestQueue = new ArrayList<>();
    }

    public void submitRequest(Request request) {
        if (elevatorCar.getState() == ElevatorState.UNDER_MAINTENANCE) {
            System.out.println("Elevator " + elevatorCar.getId() + " is under maintenance. Cannot accept request.");
            return;
        }

        processingStrategy.addRequest(requestQueue, request);
        System.out.println("Elevator " + elevatorCar.getId() + " accepted request to floor " + request.getDestinationFloor());
    }

    public void processRequests() {
        if (elevatorCar.getState() == ElevatorState.UNDER_MAINTENANCE) {
            return;
        }

        // Just a basic simulation of processing
        while (!requestQueue.isEmpty()) {
            Request req = requestQueue.remove(0); // Basic FCFS pop
            moveToFloor(req.getDestinationFloor());
        }
        
        elevatorCar.setState(ElevatorState.IDLE);
        elevatorCar.setDirection(Direction.NONE);
    }

    private void moveToFloor(int targetFloor) {
        if (elevatorCar.getCurrentFloor() < targetFloor) {
            elevatorCar.setState(ElevatorState.MOVING_UP);
            elevatorCar.setDirection(Direction.UP);
            while (elevatorCar.getCurrentFloor() < targetFloor) {
                elevatorCar.setCurrentFloor(elevatorCar.getCurrentFloor() + 1);
                System.out.println("Elevator " + elevatorCar.getId() + " moving up... currently at floor " + elevatorCar.getCurrentFloor());
            }
        } else if (elevatorCar.getCurrentFloor() > targetFloor) {
            elevatorCar.setState(ElevatorState.MOVING_DOWN);
            elevatorCar.setDirection(Direction.DOWN);
            while (elevatorCar.getCurrentFloor() > targetFloor) {
                elevatorCar.setCurrentFloor(elevatorCar.getCurrentFloor() - 1);
                System.out.println("Elevator " + elevatorCar.getId() + " moving down... currently at floor " + elevatorCar.getCurrentFloor());
            }
        }
        
        System.out.println("Elevator " + elevatorCar.getId() + " reached floor " + targetFloor + ". Opening doors.");
        elevatorCar.setDoorOpen(true);
        // ... simulated delay for people getting in/out
        elevatorCar.setDoorOpen(false);
        System.out.println("Elevator " + elevatorCar.getId() + " closing doors.");
    }

    public void setUnderMaintenance(boolean maintenance) {
        if (maintenance) {
            elevatorCar.setState(ElevatorState.UNDER_MAINTENANCE);
            elevatorCar.setDirection(Direction.NONE);
            requestQueue.clear(); 
            System.out.println("Elevator " + elevatorCar.getId() + " is now UNDER MAINTENANCE.");
        } else {
            elevatorCar.setState(ElevatorState.IDLE);
            System.out.println("Elevator " + elevatorCar.getId() + " is back online and IDLE.");
        }
    }

    public ElevatorCar getElevatorCar() {
        return elevatorCar;
    }
}
