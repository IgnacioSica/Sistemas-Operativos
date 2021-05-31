public class SchedulerInitializer implements Runnable{
    VaccinationCenter[] centers;
    String[] moments;
    int momentsIndex;
    private RequestPlanner requestPlanner;
    private VaccinesManager vaccinesManager;

    @Override
    public void run() {
        for (VaccinationCenter vaccinationCenter: centers) {
            Scheduler scheduler = new Scheduler(vaccinationCenter, moments[momentsIndex], requestPlanner, vaccinesManager);
            new Thread(scheduler).start();
        }
    }
}
