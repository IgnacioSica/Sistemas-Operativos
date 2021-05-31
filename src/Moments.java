public class Moments implements Runnable{
    VaccinationCenter[] centers;
    SchedulerInitializer schedulerInitializer;
    String[] moments;

    Moments(SchedulerInitializer schedulerInitializer){
        this.schedulerInitializer = schedulerInitializer;
    }

    private RequestPlanner requestPlanner;
    private VaccinesManager vaccinesManager;

    @Override
    public void run() {
        for(int i = 0; i< moments.length; i++){
            schedulerInitializer.setMoment(moments[i]);
            schedulerInitializer.notify();
        }
    }
}
