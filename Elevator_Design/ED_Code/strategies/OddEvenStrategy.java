package strategies;

import models.ElevatorCar;
import models.Request;
import enums.ElevatorState;
import java.util.List;

public class OddEvenStrategy implements ElevatorSelectionStrategy {

    @Override
    public ElevatorCar selectElevator(List<ElevatorCar> elevators, Request request) {
        // Find an elevator that handles the parity of the source floor, or any closest idle.
        ElevatorCar bestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (ElevatorCar elevator : elevators) {
            if (elevator.getState() == ElevatorState.UNDER_MAINTENANCE) {
                continue;
            }

            int distance = Math.abs(elevator.getCurrentFloor() - request.getSourceFloor());
            
            // basic check
            if (distance < minDistance) {
                bestElevator = elevator;
                minDistance = distance;
            }
        }
        
        return bestElevator;
    }
}
