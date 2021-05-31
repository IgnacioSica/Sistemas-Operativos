public class SchedulerInitializer implements Runnable{
    private VaccinationCenter[] centers;
    private String moment;
    private IRequestPlannerOut requestPlanner;
    private IVaccinesManagerOut vaccinesManager;

    SchedulerInitializer(IRequestPlannerOut requestPlannerOut, IVaccinesManagerOut vaccinesManagerOut){
        this.requestPlanner = requestPlannerOut;
        this.vaccinesManager = vaccinesManagerOut;
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }

    @Override
    public void run() {
        while(true){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (VaccinationCenter vaccinationCenter: centers) {
                Scheduler scheduler = new Scheduler(vaccinationCenter, moment, requestPlanner, vaccinesManager);
                new Thread(scheduler).start();
            }
        }
    }
}
