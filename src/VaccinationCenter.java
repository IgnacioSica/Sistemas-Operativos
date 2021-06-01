public class VaccinationCenter {
    int availableTurns;
    String name;
    VaccinationCenter(String centerName, int availableTurnsPerDay) {
        availableTurns = availableTurnsPerDay;
        name = centerName;
    }

    boolean availability() {
        return availableTurns > 0;
    }

    void reserveATurn() {
        availableTurns--;
    }

}
