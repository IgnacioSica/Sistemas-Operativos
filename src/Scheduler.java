import java.util.concurrent.Semaphore;

public class Scheduler extends Thread {
    VaccinationCenter center;
    Semaphore requestSem;
    Semaphore vaccineSem;
    RequestPlanner rPlanner;
    VaccinePlanner vPlanner;
    Semaphore notificationWriterSem;
    Scheduler(VaccinationCenter vaccinationCenter, Semaphore requestSemaphore, Semaphore vaccineSemaphore, RequestPlanner requestPlanner, VaccinePlanner vaccinePlanner, Semaphore notificationWriterSemaphore) {
        center = vaccinationCenter;
        requestSem = requestSemaphore;
        vaccineSem = vaccineSemaphore;
        rPlanner = requestPlanner;
        vPlanner = vaccinePlanner;
        notificationWriterSem = notificationWriterSemaphore;
    }

    @Override
    public void run() {
        try {
            while (center.availability()) {
                vaccineSem.acquire();
                requestSem.acquire();
                if (!rPlanner.isEmpty() && vPlanner.availability()) {
                    Request request = rPlanner.extractRequestByPriority();
                    vPlanner.extractVaccine();
                    center.reserveATurn();
                    new Notifier(request, center.name, notificationWriterSem).run();
                }
                vaccineSem.release();
                requestSem.release();
            }
        } catch (Exception e) {

        }
    }

}
