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
        var availability = true;
        while(vaccinationCenter.getAvailabilityByMoment(moments.getMoment()) > 0 && availability) {
            try {
                vaccineSem.acquire();
                requestSem.acquire();
                if (!rPlanner.isEmpty() && vPlanner.availability()) {
                    Request request;
                    vPlanner.extractVaccines();
                        request = rPlanner.extractRequestByPriority();
                    if(Objects.isNull(request)){
                        vPlanner.addVaccines(2);
                    } else{
                        new Notifier(request, vaccinationCenter.name()).run();
                        vaccinationCenter.scheduleByMoment(moments.getMoment());
                    }
                } else {
                    availability = false;
                }
                vaccineSem.release();
                requestSem.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        vaccinationCenter.finishDay(moments.getMoment());
        System.out.println("    Scheduler for " + vaccinationCenter + " finished");
        this.moments.ProcessFinished();
    }
}
