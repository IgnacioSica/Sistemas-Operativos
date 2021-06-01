public class VaccinePlanner {
    VaccinePlanner(){
        vaccineCount = 0;
    }
    int vaccineCount;

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
