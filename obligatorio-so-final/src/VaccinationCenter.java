import java.util.HashMap;
import java.util.Map;

public enum VaccinationCenter {
    Center_A(10),
    Center_B(5),
    Center_C(13),
    Center_D(7),
    Center_E(8),
    Center_F(9);

    public final int initialAvailability;
    public final Map<Integer, Integer> availabilityMap = new HashMap<>();

    private VaccinationCenter(int initialAvailability) {
        this.initialAvailability = initialAvailability;
    }

    public int getAvailabilityByMoment(int moment){
        availabilityMap.putIfAbsent(moment, initialAvailability);
        return availabilityMap.get(moment);
    }

    public void scheduleByMoment(int moment){
        //We add the value if it's not present on the map
        availabilityMap.putIfAbsent(moment, initialAvailability);
        availabilityMap.putIfAbsent(moment + 28 * 24, initialAvailability);
        
        //We update the values
        int newValueDosis1 = availabilityMap.get(moment) - 1;
        int newValueDosis2 = availabilityMap.get(moment + (28 * 24)) - 1;
        availabilityMap.put(moment, newValueDosis1);
        availabilityMap.put(moment + (28 * 24), newValueDosis2);
    }

    public void finishDay(int moment){
        //When a day finishes we delete the entry from the map because we won't need it anymore
        availabilityMap.remove(moment);
    }

}
