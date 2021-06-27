import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Scheduler extends Thread {
    private final Moments moments;
    private final VaccinationCenter vaccinationCenter;
    private final RequestPlanner rPlanner;
    private final VaccinePlanner vPlanner;

    Scheduler(Moments moments, VaccinationCenter vaccinationCenter, RequestPlanner rPlanner, VaccinePlanner vPlanner) {
        this.moments = moments;
        this.vaccinationCenter = vaccinationCenter;
        this.rPlanner = rPlanner;
        this.vPlanner = vPlanner;
    }

    public void run() {
        System.out.println("    Scheduler for " + vaccinationCenter + " started");
        try {
            TimeUnit.MILLISECONDS.sleep(this.moments.timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Semaphore vaccineSem = vPlanner.semaphore;
        Semaphore requestSem = rPlanner.semaphore;

        for (int i = 0; i < vaccinationCenter.getAvailability(); i++) {
            try {
                vaccineSem.acquire();
                requestSem.acquire();
                if (!rPlanner.isEmpty() && vPlanner.availability()) {
                    Request request;
                    vPlanner.extractVaccine();
                    if(vaccinationCenter.second_doses.get(moments.getMoment()).isEmpty()){
                        request = rPlanner.extractRequestByPriority();
                        new Notifier(request, vaccinationCenter.name(), "primera dosis").run();
                        vaccinationCenter.second_doses.get(moments.getMoment() + 72).add(request);
                    } else {
                        request = vaccinationCenter.second_doses.get(moments.getMoment()).remove(0);
                        new Notifier(request, vaccinationCenter.name(), "segunda dosis").run();
                    }
                    if(Objects.isNull(request)){
                        vPlanner.addVaccines(1);
                    }
                }
                if (vPlanner.availability()) {
                    Request request;
                    vPlanner.extractVaccine();
                    if(!vaccinationCenter.second_doses.get(moments.getMoment()).isEmpty()){
                        request = vaccinationCenter.second_doses.get(moments.getMoment()).remove(0);
                        new Notifier(request, vaccinationCenter.name(), "segunda dosis").run();
                    } else {
                        vPlanner.addVaccines(1);
                    }
                }
                vaccineSem.release();
                requestSem.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("    Scheduler for " + vaccinationCenter + " finished");
        this.moments.ProcessFinished();
    }
}
