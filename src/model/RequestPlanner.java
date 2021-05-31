package model;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;

public class RequestPlanner implements IRequestPlanner {
    private static HashMap<String, VaccineLine> vaccineLines;
    private static Map<String, Semaphore> semaphores;

    public static Request getHighestPriorityRequest(String lineName) {
        VaccineLine line = vaccineLines.get(lineName);

        return line != null ? line.getHighestPriorityRequest() : null;
    }

    RequestPlanner() {
        VaccineLine pfizer = new VaccineLine("pfizer");
        VaccineLine sinovac = new VaccineLine("sinovac");
        vaccineLines = new HashMap<>();
        vaccineLines.put("pfizer", pfizer);
        vaccineLines.put("sinovac", sinovac);
        Semaphore semaphorePfizer = new Semaphore(1,true);
        Semaphore semaphoreSinovac = new Semaphore(1,true);
        semaphores.put("pfizer", semaphorePfizer);
        semaphores.put("sinovac", semaphoreSinovac);
    }

    public static Semaphore getSemaphore(String line){
        Semaphore semaphore = semaphores.get(line);
        return semaphore != null ? semaphore : null;
    }

    private class VaccineLine {
        private TreeMap<String, Request> highestPriorityMap;
        private TreeMap<String, Request> highPriorityMap;
        private TreeMap<String, Request> lowPriorityMap;
        private TreeMap<String, Request> lowestPriorityMap;

        private Map<Priority, TreeMap<String, Request>> priorityMap;

        public String name;

        VaccineLine(String name) {
            this.name = name;
            highestPriorityMap = new TreeMap<>();
            highPriorityMap = new TreeMap<>();
            lowPriorityMap = new TreeMap<>();
            lowestPriorityMap = new TreeMap<>();
            priorityMap.put(Priority.highestPriority, highestPriorityMap);
            priorityMap.put(Priority.highPriority, highPriorityMap);
            priorityMap.put(Priority.lowPriority, lowPriorityMap);
            priorityMap.put(Priority.lowestPriority, lowestPriorityMap);
        }

        public void addRequest(Request request, String key, Priority priority) {
            TreeMap<String, Request> map = priorityMap.get(priority);
            map.put(key, request);
        }

        public Request getHighestPriorityRequest() {
            for (Priority priority : Priority.values()) {
                TreeMap<String, Request> map = priorityMap.get(priority);
                if (!map.isEmpty()) {
                    String key = map.lastKey();
                    Request request = map.get(key);
                    return request;
                }
            }
            return null;
        }
    }
}
