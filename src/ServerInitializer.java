import java.util.concurrent.Semaphore;

public class ServerInitializer implements Runnable {
    private String moment;
    private IVaccinesManagerIn vaccinesManagerIn;
    private IRequestPlannerIn requestPlannerIn;
    private String[] requestSources;
    private Semaphore[] requestSemaphores;
    private String[] vaccinesSources;
    private Semaphore[] vaccinesSemaphores;

    ServerInitializer(IRequestPlannerIn requestPlannerIn, IVaccinesManagerIn vaccinesManagerIn) {
        this.requestPlannerIn = requestPlannerIn;
        this.vaccinesManagerIn = vaccinesManagerIn;

        // Hardcoded sources
        requestSources = new String[]{"source.web", "source.app", "source.wpp"};
        requestSemaphores = new Semaphore[]{new Semaphore(1, true), new Semaphore(1, true), new Semaphore(1, true),};
        vaccinesSources = new String[]{"vaccines.txt"};
        vaccinesSemaphores = new Semaphore[]{new Semaphore(1, true)};
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }

    @Override
    public void run() {
        while (true) {
            /*try {
                //wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            for (int i = 0; i < requestSources.length; i++) {
                for (int j = 0; j < 6; j++) {
                    RequestReader requestReader = new RequestReader(requestSources[i], moment, requestSemaphores[i], requestPlannerIn);
                    new Thread(requestReader).start();
                }
            }

            for (int i = 0; i < vaccinesSources.length; i++) {
                for (int j = 0; j < 6; j++) {
                    VaccineReader vaccineReader = new VaccineReader(vaccinesSources[i], vaccinesSemaphores[i], moment, vaccinesManagerIn);
                    new Thread(vaccineReader).start();
                }
            }
        }
    }
}
