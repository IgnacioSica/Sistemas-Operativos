import java.util.concurrent.Semaphore;

public class Scheduler implements Runnable{
    private IVaccinationCenter vaccinationCenter;
    private String moment;
    private IVaccinesManagerOut vaccinesManager;
    private Semaphore vaccinesSemaphore;
    private IRequestPlannerOut requestPlanner;
    private Semaphore requestsSemaphore;

    public Scheduler (IVaccinationCenter vaccinationCenter, String moment, IRequestPlannerOut requestPlanner, IVaccinesManagerOut vaccinesManager){
        this.vaccinationCenter = vaccinationCenter;
        this.moment = moment;
        this.requestPlanner = requestPlanner;
        this.vaccinesManager = vaccinesManager;
    }

    @Override
    public void run() {
        int availability = vaccinationCenter.getAvailability(moment);
        int vaccines = vaccinationCenter.getVaccines(moment);

        Vaccine availableVaccines = new Vaccine("", 0);

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

        getRequestsSemaphore(availableVaccines.getVaccineType());

        vaccinationCenter.addVaccines(availableVaccines.getAmount(), moment);

        for (int i = 0; i < availableVaccines.getAmount(); i++) {
            try {
                this.requestsSemaphore.acquire();
                Request request = this.requestPlanner.getHighestPriorityRequest(availableVaccines.getVaccineType());
                this.requestsSemaphore.release();

                boolean state = this.vaccinationCenter.addRequest(request, moment);
                if(state){
                    Notifier notifier = new Notifier(/*Request*/);
                    new Thread(notifier).start();
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
