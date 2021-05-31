package model;

public interface IVaccinationCenter {
    public int getAvailability(String date);
    public int getVaccines(String date);
    public void addVaccines(int vaccines, String date);
    public boolean addRequest(Request request, String date);
}
