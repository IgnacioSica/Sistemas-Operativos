import java.util.concurrent.Semaphore;

public class Scheduler implements Runnable {
    private final IVaccinationCenter vaccinationCenter;
    private final String moment;
    private final IVaccinesManagerOut vaccinesManager;
    private final IRequestPlannerOut requestPlanner;
    private Semaphore vaccinesSemaphore;
    private Semaphore requestsSemaphore;

    public Scheduler(IVaccinationCenter vaccinationCenter, String moment, IRequestPlannerOut requestPlanner, IVaccinesManagerOut vaccinesManager) {
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

        if (!getVaccinesSemaphore())
            return;

        if (availability - vaccines > 0) {
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

        if (!getRequestsSemaphore(availableVaccines.getVaccineType()))
            return;

        vaccinationCenter.addVaccines(availableVaccines.getAmount(), moment);

        for (int i = 0; i < availableVaccines.getAmount(); i++) {
            try {
                this.requestsSemaphore.acquire();
                Request request = this.requestPlanner.getHighestPriorityRequest(availableVaccines.getVaccineType());
                this.requestsSemaphore.release();

                boolean state = this.vaccinationCenter.addRequest(request, moment);
                if (state) {
                    Notifier notifier = new Notifier(/*Request*/);
                    new Thread(notifier).start();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean getRequestsSemaphore(String line) {
        this.requestsSemaphore = this.requestPlanner.getSemaphore(line);
        return requestsSemaphore != null;
    }

    private boolean getVaccinesSemaphore() {
        this.vaccinesSemaphore = this.vaccinesManager.getSemaphore();
        return vaccinesSemaphore != null;
    }
}
