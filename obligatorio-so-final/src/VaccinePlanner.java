import java.util.concurrent.Semaphore;

public class VaccinePlanner {
    int vaccineCount;
    Semaphore semaphore;
    VaccinePlanner() {
        vaccineCount = 0;
        this.semaphore = new Semaphore(1, true);
    }

    void addVaccines(int amount) {
        vaccineCount += amount;
    }

    boolean availability() {
        return vaccineCount > 1;
    }

    void extractVaccines() {
        vaccineCount -= 2;
    }
}
