public class SchedulerInitializer implements Runnable {
    private final VaccinationCenter[] vaccinationCenters;
    private String moment;
    private final IRequestPlannerOut requestPlanner;
    private final IVaccinesManagerOut vaccinesManager;
    private Moments moments;

    SchedulerInitializer(IRequestPlannerOut requestPlannerOut, IVaccinesManagerOut vaccinesManagerOut, VaccinationCenter[] vaccinationCenters) {
        this.requestPlanner = requestPlannerOut;
        this.vaccinesManager = vaccinesManagerOut;
        this.vaccinationCenters = vaccinationCenters;
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }

    public void setMoment(Moments moment) {
        this.moments = moments;
    }

    @Override
    public void run() {
        while (true) {
            /*synchronized (this){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            for (VaccinationCenter vaccinationCenter : vaccinationCenters) {
                Scheduler scheduler = new Scheduler(vaccinationCenter, moment, requestPlanner, vaccinesManager);
                new Thread(scheduler).start();
            }
        }
    }
}
