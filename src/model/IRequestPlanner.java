package model;

import java.util.concurrent.Semaphore;

public interface IRequestPlanner {
    void addLine(String lineName);
    Semaphore getSemaphore(String line);
    Request getHighestPriorityRequest(String lineName);
    boolean addRequest(Request request, String key, String lineName, Priority priority);
}
