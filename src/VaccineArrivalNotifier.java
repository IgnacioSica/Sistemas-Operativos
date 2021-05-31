import java.util.AbstractMap;
import java.util.concurrent.Semaphore;

public class VaccineArrivalNotifier implements Runnable{
    private AbstractMap.SimpleEntry<String, Integer> vaccines;
    private IVaccinesManagerIn vaccinesManagerIn;
    private Semaphore semaphore;

    private String source;
    private String moment;

    VaccineArrivalNotifier(String source, String moment, IVaccinesManagerIn vaccinesManagerIn){
        this.source = source;
        this.moment = moment;
        this.vaccines = readNotification(source, moment);
        this.vaccinesManagerIn = vaccinesManagerIn;
    }

    private AbstractMap.SimpleEntry<String, Integer> readNotification(String source, String moment){
        // Todo
        return null;
    }

    @Override
    public void run() {
        this.semaphore = this.vaccinesManagerIn.getSemaphore();
        try {
            this.semaphore.acquire();
            vaccinesManagerIn.addVaccines(vaccines.getKey(), vaccines.getValue());
            this.semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
