import model.RequestPlanner;
import model.VaccinationCenter;

public class SchedulerInitializer implements Runnable{
    VaccinationCenter[] centers;
    String[] moments;
    int momentsIndex;
    private RequestPlanner requestPlanner;

    @Override
    public void run() {
        for (VaccinationCenter vaccinationCenter: centers) {
            Scheduler scheduler = new Scheduler(vaccinationCenter, moments[momentsIndex], requestPlanner);
            new Thread(scheduler).start();
        }
    }
}
