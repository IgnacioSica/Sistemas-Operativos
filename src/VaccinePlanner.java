public class VaccinePlanner {
    int vaccineCount;

    VaccinePlanner() {
        vaccineCount = 0;
    }

    void addVaccines(int amount) {
        vaccineCount += amount;
    }

    boolean availability() {
        return vaccineCount > 0;
    }

    void extractVaccine() {
        vaccineCount--;
    }
}
