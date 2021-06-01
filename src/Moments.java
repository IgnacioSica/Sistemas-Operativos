import java.util.concurrent.Semaphore;

public class Moments implements Runnable {
    private final VaccinationCenter[] centers;
    private final SchedulerInitializer schedulerInitializer;
    private final ServerInitializer serverInitializer;
    private final Semaphore semaphore;
    private final String[] moments;

    private int threadsRunning = 0;
    private RequestPlanner requestPlanner;
    private VaccinesManager vaccinesManager;
    Moments(SchedulerInitializer schedulerInitializer, ServerInitializer serverInitializer, VaccinationCenter[] centers) {
        this.schedulerInitializer = schedulerInitializer;
        this.serverInitializer = serverInitializer;
        this.centers = centers;

        this.semaphore = new Semaphore(1, true);

        //hardcoded moments
        moments = new String[]{"24/06/21", "25/06/21", "26/06/21", "27/06/21", "28/06/21", "29/06/21", "30/06/21", "01/07/21"};
    }

    public synchronized void addThread() throws InterruptedException {
        threadsRunning++;
        if (threadsRunning == 1)
            this.semaphore.acquire();
    }

    public synchronized void removeThread() {
        threadsRunning--;
        if (threadsRunning == 0) {
            this.semaphore.release();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < moments.length; i++) {
            System.out.println("Comienza el dia " + moments[i]);
            for (VaccinationCenter vc : centers) {
                vc.nextMoment(moments[i]);
            }
            schedulerInitializer.setMoment(moments[i]);
            serverInitializer.setMoment(moments[i]);

            /*synchronized (schedulerInitializer){
                schedulerInitializer.notify();
            }*/

            /*synchronized (serverInitializer){
                serverInitializer.notify();
            }*/
            try {
                this.semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
