public class main {
    public static void main(String[] args) {
        RequestPlanner requestPlanner = new RequestPlanner();
        requestPlanner.addLine("pfizer");
        requestPlanner.addLine("sinovac");

        VaccinationCenter vc1 = new VaccinationCenter(300);
        VaccinationCenter vc2 = new VaccinationCenter(250);
        VaccinationCenter vc3 = new VaccinationCenter(175);
        VaccinationCenter[] vaccinationCenters = new VaccinationCenter[]{vc1, vc2, vc3};

        VaccinesManager vaccinesManager = new VaccinesManager();

        SchedulerInitializer schedulerInitializer = new SchedulerInitializer(requestPlanner, vaccinesManager, vaccinationCenters);
        ServerInitializer serverInitializer = new ServerInitializer(requestPlanner, vaccinesManager);

        Moments moments = new Moments(schedulerInitializer, serverInitializer, vaccinationCenters);

        new Thread(schedulerInitializer).start();
        new Thread(serverInitializer).start();

        new Thread(moments).start();
    }
}
