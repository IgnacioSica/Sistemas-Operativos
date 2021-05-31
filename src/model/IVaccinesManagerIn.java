package model;

import java.util.concurrent.Semaphore;

public interface IVaccinesManagerIn {
    Semaphore getSemaphore();
    boolean addVaccines(String line, int amount);
}
