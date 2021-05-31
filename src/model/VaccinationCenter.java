package model;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class VaccinationCenter implements  IVaccinationCenter, IVaccinationCenterMoments{
    private HashMap<String, Integer> availableVaccines;
    private HashMap<String, Integer> availability;
    private HashMap<String, ArrayList> requests;

    private int capacity;

    public VaccinationCenter (int capacity){
        this.availableVaccines = new HashMap<>();
        this.availability = new HashMap<>();
        this.requests = new HashMap<>();
        this.capacity = capacity;
    }

    public boolean addRequest(Request request, String date){
        if(availableVaccines.get(date) > 0){
            requests.get(date).add(request);
            return true;
        }
        return false;
    }

    public void addVaccines(int vaccines, String date){
        int currentAvailableVaccines = this.availableVaccines.get(date);
        this.availableVaccines.put(date, currentAvailableVaccines + vaccines);
    }

    public int getAvailability(String date){
        return availability.get(date);
    }

    public int getVaccines(String date){
        return availableVaccines.get(date);
    }

    public void nextMoment(String date){
        availableVaccines.put(date, 0);
        availability.put(date, randomAvailability());
        requests.put(date, new ArrayList());
    }

    private int randomAvailability(){
        Random rand = new Random();
        return rand.nextInt(capacity);
    }
}
