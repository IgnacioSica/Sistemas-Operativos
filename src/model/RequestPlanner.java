package model;

import java.util.HashMap;
import java.util.TreeMap;

public class RequestPlanner implements IRequestPlanner{
    private static HashMap<String, VaccineLine> vaccineLines;

    public static Request getHighestPriorityRequest(String lineName){
        VaccineLine line = vaccineLines.get(lineName);

        return line != null ? line.getHighestPriorityRequest() : null;
    }

    RequestPlanner(){
        VaccineLine pfizer = new VaccineLine("pfizer");
        VaccineLine sinovac = new VaccineLine("sinovac");
        vaccineLines = new HashMap<>();
        vaccineLines.put("pfizer", pfizer);
        vaccineLines.put("sinovac", sinovac);
    }

    private class VaccineLine{
        private TreeMap<String, Request> highestPriority;
        private TreeMap<String, Request> highPriority;
        private TreeMap<String, Request> lowPriority;
        private TreeMap<String, Request> lowestPriority;

        public String name;

        VaccineLine(String name){
            this.name = name;
            highestPriority  = new TreeMap<>();
            highPriority = new TreeMap<>();
            lowPriority = new TreeMap<>();
            lowestPriority = new TreeMap<>();
        }

        public Request getHighestPriorityRequest(){
            Request request;
            if(!highestPriority.isEmpty()){
                String key = highestPriority.lastKey();
                request = highestPriority.remove(key);
                return request;
            }

            if(!highPriority.isEmpty()){
                String key = highPriority.lastKey();
                request = highPriority.remove(key);
                return request;
            }

            if(!lowPriority.isEmpty()){
                String key = lowPriority.lastKey();
                request = lowPriority.remove(key);
                return request;
            }

            if(!lowestPriority.isEmpty()){
                String key = lowestPriority.lastKey();
                request = lowestPriority.remove(key);
                return request;
            }
            return null;
        }
    }
}
