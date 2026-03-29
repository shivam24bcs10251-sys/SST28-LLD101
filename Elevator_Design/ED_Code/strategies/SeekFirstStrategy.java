package strategies;

import models.Request;
import enums.Direction;
import java.util.List;

public class SeekFirstStrategy implements RequestProcessingStrategy {

    @Override
    public void processRequests(List<Request> requestQueue, Runnable moveUp, Runnable moveDown, Runnable openDoors) {
        // In fully implemented Seek/SCAN:
        // sort elements based on direction. Let's keep it simple for interface context.
    }

    @Override
    public void addRequest(List<Request> requestQueue, Request request) {
        requestQueue.add(request); // sophisticated add would place it in sorted scan order
    }
}
