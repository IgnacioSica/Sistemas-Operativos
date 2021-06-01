import java.util.concurrent.Semaphore;

public class VaccineManager extends Thread{
    VaccineManager(String data, Semaphore vaccineProcessorSemaphore, VaccinePlanner vaccinePlanner){
        vaccineData = data;
        vaccineProcessorSem = vaccineProcessorSemaphore;
        planner = vaccinePlanner;
    }

    String vaccineData;
    Semaphore vaccineProcessorSem;
    VaccinePlanner planner;

    @Override
    public void run(){
        try{
            int vaccinesAmount = Integer.parseInt(vaccineData);
            vaccineProcessorSem.acquire();
            planner.addVaccines(vaccinesAmount);
            System.out.println("added " + vaccinesAmount + " vaccines.");
            vaccineProcessorSem.release();
        }catch(Exception e){
            System.out.println("VaccineManager error");
        }
    }
    
}
