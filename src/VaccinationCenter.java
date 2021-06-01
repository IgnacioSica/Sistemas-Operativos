public class VaccinationCenter {
    VaccinationCenter(String centerName, int availableTurnsPerDay){
        availableTurns = availableTurnsPerDay;
        name = centerName;
    }
    
    int availableTurns;
    String name;
    
    boolean availability(){
        return availableTurns > 0;
    }
    void reserveATurn(){
        availableTurns--;
    }

}
