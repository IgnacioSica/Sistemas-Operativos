import java.util.AbstractMap;
import java.util.concurrent.Semaphore;

public class Scheduler implements Runnable{
    private IVaccinationCenter vaccinationCenter;
    private String date;
    private IVaccinesManagerOut vaccinesManager;
    private Semaphore vaccinesSemaphore;
    private IRequestPlannerOut requestPlanner;
    private Semaphore requestsSemaphore;

    public Scheduler (IVaccinationCenter vaccinationCenter, String date, IRequestPlannerOut requestPlanner, IVaccinesManagerOut vaccinesManager){
        this.vaccinationCenter = vaccinationCenter;
        this.date = date;
        this.requestPlanner = requestPlanner;
        this.vaccinesManager = vaccinesManager;
    }

    @Override
    public void run() {
        int availability = vaccinationCenter.getAvailability(date);
        int vaccines = vaccinationCenter.getVaccines(date);

        AbstractMap.SimpleEntry<String, Integer> availableVaccines = new AbstractMap.SimpleEntry<String, Integer>("", 0);

        getVaccinesSemaphore();

        if(availability - vaccines > 0){
            try {
                this.vaccinesSemaphore.acquire();
                availableVaccines = vaccinesManager.getVaccines(availability - vaccines);
                this.vaccinesSemaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            return;
        }

        getRequestsSemaphore(availableVaccines.getKey());

        vaccinationCenter.addVaccines(availableVaccines.getValue(), date);

        while(availableVaccines.getValue() > 0){
            try {
                this.requestsSemaphore.acquire();
                Request request = this.requestPlanner.getHighestPriorityRequest(availableVaccines.getKey());
                this.requestsSemaphore.release();

                boolean state = this.vaccinationCenter.addRequest(request, date);
                if(state){
                    Notifier notifier = new Notifier(/*Request*/);
                    new Thread(notifier).start();
                    availableVaccines.setValue(availableVaccines.getValue() - 1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean getRequestsSemaphore(String line) {
        this.vaccinesSemaphore = this.requestPlanner.getSemaphore(line);
        return vaccinesSemaphore != null ? true : false;
    }

    private boolean getVaccinesSemaphore() {
        this.requestsSemaphore = this.vaccinesManager.getSemaphore();
        return requestsSemaphore != null ? true : false;
    }
}
