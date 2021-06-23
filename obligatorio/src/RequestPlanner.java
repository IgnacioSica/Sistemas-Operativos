import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;

public class RequestPlanner implements IRequestPlannerIn, IRequestPlannerOut {
    private final HashMap<String, VaccineLine> vaccineLines;
    private final Map<String, Semaphore> semaphores;

    public RequestPlanner() {
        vaccineLines = new HashMap<>();
        semaphores = new HashMap<>();
    }

    public void addLine(String lineName) {
        VaccineLine line = new VaccineLine(lineName);
        vaccineLines.put(lineName, line);
        Semaphore semLine = new Semaphore(1, true);
        semaphores.put(lineName, semLine);
    }

    public Semaphore getSemaphore(String line) {
        return semaphores.get(line);
    }

    public boolean addRequest(Request request, String key, String lineName, Priority priority) {
        VaccineLine line = vaccineLines.get(lineName);
        if (Objects.isNull(line))
            return false;

        line.addRequest(request, key, priority);

        return true;
    }

    // synchronized
    public Request getHighestPriorityRequest(String lineName) {
        VaccineLine line = vaccineLines.get(lineName);

        return line != null ? line.getHighestPriorityRequest() : null;
    }

    private class VaccineLine { // cola de solicitudes segun el tipo de vacuna
        private final Map<Priority, TreeMap<String, Request>> priorityMap;
        public String name;

        VaccineLine(String name) {
            this.name = name;
            this.priorityMap = new TreeMap<>();
            for (Priority priority : Priority.values()) {
                TreeMap<String, Request> map = new TreeMap<>();
                this.priorityMap.put(priority, map);
            }
        }

        public void addRequest(Request request, String key, Priority priority) {
            TreeMap<String, Request> map = priorityMap.get(priority);
            map.put(key, request);
        }

        public Request getHighestPriorityRequest() {
            for (Priority priority : Priority.values()) {
                TreeMap<String, Request> map = priorityMap.get(priority);
                if (!map.isEmpty()) {
                    String key = map.lastKey(); // llave de mayor valor

                    return map.remove(key);
                }
            }

            return null;
        }
    }
}
