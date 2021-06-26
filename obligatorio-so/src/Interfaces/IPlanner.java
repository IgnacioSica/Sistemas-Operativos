package Interfaces;

import Utils.Priority;

import java.util.concurrent.Semaphore;

public interface IPlanner<T> {
    Semaphore getSemaphore(String key);

    void addQueue(String queueType);

    boolean addElement(T element);

    T getElement(String queueType);
}