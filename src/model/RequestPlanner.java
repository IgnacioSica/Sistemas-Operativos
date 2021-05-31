package model;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;

public class RequestPlanner implements IRequestPlanner {
    private HashMap<String, VaccineLine> vaccineLines;
    private Map<String, Semaphore> semaphores;

    public RequestPlanner() {
        vaccineLines = new HashMap<>();
        semaphores = new HashMap<>();
    }

    public void addLine(String lineName){
        VaccineLine line = new VaccineLine(lineName);
        vaccineLines.put(lineName, line);
        Semaphore semLine = new Semaphore(1,true);
        semaphores.put(lineName, semLine);
    }

    public Semaphore getSemaphore(String line){
        Semaphore semaphore = semaphores.get(line);
        return semaphore != null ? semaphore : null;
    }

    public Request getHighestPriorityRequest(String lineName) {
        VaccineLine line = vaccineLines.get(lineName);
        return line != null ? line.getHighestPriorityRequest() : null;
    }

    private class VaccineLine {
        private Map<Priority, TreeMap<String, Request>> priorityMap;
        public String name;

        VaccineLine(String name) {
            this.name = name;
            for (Priority priority: Priority.values()) {
                TreeMap<String, Request> map = new TreeMap<>();
                priorityMap.put(priority, map);
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
                    String key = map.lastKey();
                    Request request = map.get(key);
                    return request;
                }
            }
            return null;
        }
    }
}
