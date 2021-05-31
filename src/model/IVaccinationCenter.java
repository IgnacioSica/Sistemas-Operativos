package model;

public interface IVaccinationCenter {
    int getAvailability(String date);
    void addVaccines(int vaccines, String date);
    int getVaccines(String date);
    boolean addRequest(Request request, String date);
}
