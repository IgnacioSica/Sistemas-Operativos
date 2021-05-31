import java.util.AbstractMap;
import java.util.concurrent.Semaphore;

public class VaccineArrivalNotifier implements Runnable{
    private AbstractMap.SimpleEntry<String, Integer> vaccines;
    private IVaccinesManagerIn vaccinesManagerIn;
    private Semaphore semaphore;

    VaccineArrivalNotifier(AbstractMap.SimpleEntry<String, Integer> vaccines, IVaccinesManagerIn vaccinesManagerIn){
        this.vaccines = vaccines;
        this.vaccinesManagerIn = vaccinesManagerIn;
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
