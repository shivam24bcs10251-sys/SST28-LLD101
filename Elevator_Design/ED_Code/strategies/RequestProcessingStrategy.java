package strategies;

import models.Request;
import java.util.List;

public interface RequestProcessingStrategy {
    void processRequests(List<Request> requestQueue, Runnable moveUp, Runnable moveDown, Runnable openDoors);
    void addRequest(List<Request> requestQueue, Request request);
}
