public class SchedulerInitializer extends Thread {
    private Moments moments;
    private RequestPlanner rPlanner;
    private VaccinePlanner vPlanner;

    SchedulerInitializer(Moments moments, RequestPlanner rPlanner, VaccinePlanner vPlanner) {
        this.moments = moments;
        this.rPlanner = rPlanner;
        this.vPlanner = vPlanner;
    }

    public void run() {
        for (VaccinationCenter vc : VaccinationCenter.values()) {
            Scheduler scheduler = new Scheduler(this.moments, vc, rPlanner, vPlanner);
            this.moments.newProcessStarted();
            scheduler.start();
        }
        this.moments.ProcessFinished();
    }
}
