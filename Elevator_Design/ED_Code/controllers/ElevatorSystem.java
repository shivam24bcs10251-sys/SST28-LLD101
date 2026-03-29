package controllers;

import models.Floor;
import models.Request;
import models.ElevatorCar;
import enums.Direction;
import enums.ElevatorState;
import strategies.ElevatorSelectionStrategy;

import java.util.ArrayList;
import java.util.List;

public class ElevatorSystem {
    private static ElevatorSystem instance;
    private List<ElevatorController> elevatorControllers;
    private List<Floor> floors;
    private ElevatorSelectionStrategy selectionStrategy;
    private boolean alarmTriggered = false;

    private ElevatorSystem(List<ElevatorController> elevatorControllers, List<Floor> floors, ElevatorSelectionStrategy selectionStrategy) {
        this.elevatorControllers = elevatorControllers;
        this.floors = floors;
        this.selectionStrategy = selectionStrategy;
    }

    // Singleton Pattern for Global System Coordinator
    public static void initialize(List<ElevatorController> elevatorControllers, List<Floor> floors, ElevatorSelectionStrategy selectionStrategy) {
        if (instance == null) {
            instance = new ElevatorSystem(elevatorControllers, floors, selectionStrategy);
        }
    }

    public static ElevatorSystem getInstance() {
        return instance;
    }

    public void acceptHallCall(int floorId, Direction direction) {
        if (alarmTriggered) {
            System.out.println("ALARM ACTIVE: System halted. Hall call disabled.");
            return;
        }

        System.out.println("\nHall Call: Floor " + floorId + " wants to go " + direction);
        Request hallRequest = new Request(floorId, floorId, direction);
        
        List<ElevatorCar> cars = new ArrayList<>();
        for (ElevatorController ctrl : elevatorControllers) {
            cars.add(ctrl.getElevatorCar());
        }

        ElevatorCar selectedCar = selectionStrategy.selectElevator(cars, hallRequest);

        if (selectedCar != null) {
            System.out.println("ElevatorSystem: Selected Elevator " + selectedCar.getId() + " for request from floor " + floorId);
            for (ElevatorController ctrl : elevatorControllers) {
                if (ctrl.getElevatorCar().getId() == selectedCar.getId()) {
                    ctrl.submitRequest(hallRequest);
                    break;
                }
            }
        } else {
            System.out.println("ElevatorSystem: No available elevator found to handle request.");
        }
    }

    public void triggerAlarm() {
        System.out.println("\n!!! EMERGENCY ALARM TRIGGERED !!!");
        System.out.println("Ringing Bell: DING DING DING");
        this.alarmTriggered = true;

        for (ElevatorController controller : elevatorControllers) {
            controller.getElevatorCar().setState(ElevatorState.IDLE);
            controller.getElevatorCar().setDirection(Direction.NONE);
            System.out.println("Elevator " + controller.getElevatorCar().getId() + " emergency stopped at floor " + controller.getElevatorCar().getCurrentFloor());
        }
    }

    public void resetAlarm() {
        this.alarmTriggered = false;
        System.out.println("\nAlarm Reset. System returning to normal operations.");
    }

    // Allows demo scenarios to re-initialize the singleton cleanly
    public static void reset() {
        instance = null;
    }
}
