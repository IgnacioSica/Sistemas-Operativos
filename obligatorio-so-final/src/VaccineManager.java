public class VaccineManager extends Thread {
    String vaccineData;
    VaccinePlanner planner;
    VaccineManager(String data, VaccinePlanner vaccinePlanner) {
        vaccineData = data;
        planner = vaccinePlanner;
    }

    @Override
    public void run() {
        try {
            int vaccinesAmount = Integer.parseInt(vaccineData);
            planner.semaphore.acquire();
            planner.addVaccines(vaccinesAmount);
            System.out.println("        " + vaccinesAmount + " vacunas agregadas.");
            planner.semaphore.release();
        } catch (Exception e) {
            System.out.println("        VaccineManager error");
        }
    }

}
