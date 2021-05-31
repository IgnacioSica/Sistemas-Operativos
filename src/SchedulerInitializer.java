import model.VaccinationCenter;

public class SchedulerInitializer extends Thread{
    VaccinationCenter[] centers;
    String[] moments;
    int momentsIndex;

    @Override
    public void run() {
        for (VaccinationCenter vaccinationCenter: centers) {
            Scheduler scheduler = new Scheduler(vaccinationCenter, moments[momentsIndex]);
            new Thread(scheduler).start();
        }
    }
}
