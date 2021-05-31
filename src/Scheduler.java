import model.*;

import java.util.concurrent.Semaphore;

public class Scheduler implements Runnable{
    private IVaccinationCenter vaccinationCenter;
    private String date;
    private Semaphore vaccinesSemaphore;
    private Semaphore requestsSemaphore;

    public Scheduler (IVaccinationCenter vaccinationCenter, String date){
        this.vaccinationCenter = vaccinationCenter;
        this.date = date;
    }

    @Override
    public void run() {
        int availability = vaccinationCenter.getAvailability(date);
        int vaccines = vaccinationCenter.getVaccines(date);
        int acquiredVaccines = 0;
        String vaccineLine = "";

        if(availability - vaccines > 0){
            try {
                this.vaccinesSemaphore.acquire();
                acquiredVaccines = Vaccines.getVaccines(availability - vaccines);
                // ask for vaccine name

                this.vaccinesSemaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        getRequestsSemaphore(vaccineLine);

        vaccinationCenter.addVaccines(acquiredVaccines, date);

        while(acquiredVaccines > 0){
            try {
                this.requestsSemaphore.acquire();
                Request request = RequestPlanner.getHighestPriorityRequest(vaccineLine);
                this.requestsSemaphore.release();

                boolean state = this.vaccinationCenter.addRequest(request, date);
                if(state){
                    Notifier notifier = new Notifier(/*Request*/);
                    new Thread(notifier).start();
                    acquiredVaccines--;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean getRequestsSemaphore(String line) {
        this.requestsSemaphore = RequestPlanner.getSemaphore(line);
        return requestsSemaphore != null ? true : false;
    }
}
