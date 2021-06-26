public class VaccineManager extends Thread{
    VaccineManager(String data, VaccinePlanner vaccinePlanner){
        vaccineData = data;
        planner = vaccinePlanner;
    }

    String vaccineData;
    VaccinePlanner planner;

    @Override
    public void run(){
        try{
            int vaccinesAmount = Integer.parseInt(vaccineData);
            planner.semaphore.acquire();
            planner.addVaccines(vaccinesAmount);
            System.out.println("added " + vaccinesAmount + " vaccines.");
            planner.semaphore.release();
        }catch(Exception e){
            System.out.println("VaccineManager error");
        }
    }
    
}
