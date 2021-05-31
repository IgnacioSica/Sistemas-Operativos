public class main {
    public static void main(String[] args) {
        RequestPlanner requestPlanner = new RequestPlanner();
        requestPlanner.addLine("pfizer");
        requestPlanner.addLine("sinovac");

        VaccinesManager vaccinesManager = new VaccinesManager();

        SchedulerInitializer schedulerInitializer = new SchedulerInitializer(requestPlanner, vaccinesManager);
        ServerInitializer serverInitializer = new ServerInitializer(vaccinesManager);

        Moments moments = new Moments(schedulerInitializer, serverInitializer);
    }
}
