package model;

import java.util.concurrent.Semaphore;

public interface IRequestPlannerIn {
    void addLine(String lineName);
    Semaphore getSemaphore(String line);
    boolean addRequest(Request request, String key, String lineName, Priority priority);
}
