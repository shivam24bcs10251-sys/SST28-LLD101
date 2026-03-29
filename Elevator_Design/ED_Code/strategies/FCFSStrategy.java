package strategies;

import models.Request;
import java.util.List;

public class FCFSStrategy implements RequestProcessingStrategy {

    @Override
    public void processRequests(List<Request> requestQueue, Runnable moveUp, Runnable moveDown, Runnable openDoors) {
        if (requestQueue.isEmpty()) return;

        Request currentRequest = requestQueue.get(0);
        // Assuming the caller has context to move elevator appropriately
        // Since FCFS simply takes the first request and moves towards it.
    }

    @Override
    public void addRequest(List<Request> requestQueue, Request request) {
        requestQueue.add(request);
    }
}
