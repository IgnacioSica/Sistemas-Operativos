import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Moments implements Runnable {
    public int timeout;
    private int running;
    private int moment;
    private int max;
    private RequestPlanner rPlanner;
    private VaccinePlanner vPlanner;

    public Moments(int max, int timeout, RequestPlanner rPlanner, VaccinePlanner vPlanner) {
        this.max = max;
        this.timeout = timeout;
        this.rPlanner = rPlanner;
        this.vPlanner = vPlanner;
        moment = 1;
    }

    public int getMoment(){
        return moment;
    }

    public synchronized void newProcessStarted() {
        running++;
    }

    public synchronized void ProcessFinished() {
        running--;
    }

    @Override
    public void run() {
        int[] momentsWithVaccineArrival = {1, new Random().nextInt(100) + 1};
        while (moment <= max) {
            //rPlanner.printByLevel();

            System.out.println("Current moment: " + moment);
            for (var i = 0; i < momentsWithVaccineArrival.length; i++) {
                if (moment == momentsWithVaccineArrival[i]) {
                    VaccinesReader reader = new VaccinesReader(this, i, vPlanner);
                    this.newProcessStarted();
                    new Thread(reader).start();
                }
            }
            for (Source s : Source.values()) {
                int lines = new Random().nextInt(10) + 1;
                VaccinationRequestReader reader = new VaccinationRequestReader(this, s, lines, rPlanner);
                this.newProcessStarted();
                new Thread(reader).start();
            }

            if (moment % 24 == 0) {
                SchedulerInitializer schedulerInitializer = new SchedulerInitializer(this, rPlanner, vPlanner);
                this.newProcessStarted();
                schedulerInitializer.start();
            }

            try {
                TimeUnit.MILLISECONDS.sleep(this.timeout);
                while (running > 0) {
                    TimeUnit.MILLISECONDS.sleep(this.timeout);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            moment++;
        }
        System.out.println("Simulation finished");
    }
}
