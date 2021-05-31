public class ServerInitializer implements Runnable {
    private String moment;
    private IVaccinesManagerIn vaccinesManagerIn;
    private String[] requestSources;
    private String[] vaccinesSources;

    ServerInitializer(IVaccinesManagerIn vaccinesManagerIn) {
        this.vaccinesManagerIn = vaccinesManagerIn;

        // Hardcoded sources
        requestSources = new String[]{"source.web", "source.app", "source.wpp"};
        vaccinesSources = new String[]{"vaccines.txt"};
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }

    @Override
    public void run() {
        while (true) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < requestSources.length; i++) {
                for (int j = 0; j < 6; j++) {
                    RequestManager requestManager = new RequestManager(requestSources[i], moment);
                    new Thread(requestManager).start();
                }
            }

            for (int i = 0; i < vaccinesSources.length; i++) {
                for (int j = 0; j < 6; j++) {
                    VaccineArrivalNotifier vaccineArrivalNotifier = new VaccineArrivalNotifier(vaccinesSources[i], moment, vaccinesManagerIn);
                    new Thread(vaccineArrivalNotifier).start();
                }
            }
        }
    }
}
