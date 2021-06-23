import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class VaccinesManager implements IVaccinesManagerIn, IVaccinesManagerOut {
    Map<String, Integer> vaccinesMap;
    Semaphore semaphore;

    VaccinesManager() {
        this.vaccinesMap = new HashMap<>();
        this.semaphore = new Semaphore(1, true);
    }

    private static <K, V extends Comparable<V>> Map.Entry<K, V> maxUsingIteration(Map<K, V> map) {
        Map.Entry<K, V> maxEntry = null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue()
                    .compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }

    @Override
    public Vaccine getVaccines(int vaccines) {
        Vaccine availableVaccines;
        Map.Entry<String, Integer> maxVaccines = maxUsingIteration(vaccinesMap);

        if (maxVaccines.getValue() >= vaccines) {
            availableVaccines = new Vaccine(maxVaccines.getKey(), vaccines);
            vaccinesMap.replace(maxVaccines.getKey(), maxVaccines.getValue() - vaccines);
        } else {
            availableVaccines = new Vaccine(maxVaccines.getKey(), maxVaccines.getValue());
            vaccinesMap.replace(maxVaccines.getKey(), 0);
        }

        return availableVaccines;
    }

    @Override
    public Semaphore getSemaphore() {
        return semaphore;
    }

    @Override
    public boolean addVaccines(String line, int amount) {
        if (!vaccinesMap.containsKey(line))
            return false;
        vaccinesMap.replace(line, vaccinesMap.get(line) + amount);
        return true;
    }

}
