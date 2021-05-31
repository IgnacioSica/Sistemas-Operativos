public class Moments implements Runnable {
    VaccinationCenter[] centers;
    SchedulerInitializer schedulerInitializer;
    ServerInitializer serverInitializer;
    String[] moments;

    Moments(SchedulerInitializer schedulerInitializer, ServerInitializer serverInitializer) {
        this.schedulerInitializer = schedulerInitializer;
        this.serverInitializer = serverInitializer;

        //hardcoded moments
        moments = new String[]{"24/06/21", "25/06/21", "26/06/21", "27/06/21", "28/06/21", "29/06/21", "30/06/21", "01/07/21"};
    }

    private RequestPlanner requestPlanner;
    private VaccinesManager vaccinesManager;

    @Override
    public void run() {
        for (int i = 0; i < moments.length; i++) {
            schedulerInitializer.setMoment(moments[i]);
            schedulerInitializer.notify();
            serverInitializer.setMoment(moments[i]);
            serverInitializer.notify();
        }
    }
}
