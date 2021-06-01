public class SchedulerInitializer implements Runnable{
    private VaccinationCenter[] vaccinationCenters;
    private String moment;
    private IRequestPlannerOut requestPlanner;
    private IVaccinesManagerOut vaccinesManager;

    SchedulerInitializer(IRequestPlannerOut requestPlannerOut, IVaccinesManagerOut vaccinesManagerOut, VaccinationCenter[] vaccinationCenters){
        this.requestPlanner = requestPlannerOut;
        this.vaccinesManager = vaccinesManagerOut;
        this.vaccinationCenters = vaccinationCenters;
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }

    @Override
    public void run() {
        while(true){
            /*try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            for (VaccinationCenter vaccinationCenter: vaccinationCenters) {
                Scheduler scheduler = new Scheduler(vaccinationCenter, moment, requestPlanner, vaccinesManager);
                new Thread(scheduler).start();
            }
        }
    }
}
