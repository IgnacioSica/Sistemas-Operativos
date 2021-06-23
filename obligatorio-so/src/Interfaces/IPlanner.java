package Interfaces;

import Utils.Priority;

import java.util.concurrent.Semaphore;

public interface IPlanner<T> {
    Semaphore getSemaphore(String key);

    void addQueue(String queueType);

    boolean addElement(String queueType, String key, T element, Priority priority);

    T getElement(String queueType);
}