package strategies;

import models.ElevatorCar;
import models.Request;
import java.util.List;

public interface ElevatorSelectionStrategy {
    ElevatorCar selectElevator(List<ElevatorCar> elevators, Request request);
}
