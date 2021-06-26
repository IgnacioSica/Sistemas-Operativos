import java.util.concurrent.Semaphore;

public class VaccinePlanner {
    VaccinePlanner(){
        vaccineCount = 0;
        this.semaphore = new Semaphore(1);
    }
    int vaccineCount;
    Semaphore semaphore;

    void addVaccines(int amount){
        vaccineCount += amount;
    }

    boolean availability(){
        return vaccineCount > 0;
    }

    void extractVaccine(){
        vaccineCount--;
    }
}
