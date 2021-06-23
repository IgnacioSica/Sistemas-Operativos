import java.util.concurrent.Semaphore;

public class VaccineProcessor implements Runnable {
    private final Vaccine vaccine;
    private final IVaccinesManagerIn vaccinesManagerIn;
    private final Semaphore semaphore;

    VaccineProcessor(Vaccine vaccine, IVaccinesManagerIn vaccinesManagerIn) {
        this.vaccine = vaccine;
        this.vaccinesManagerIn = vaccinesManagerIn;
        this.semaphore = this.vaccinesManagerIn.getSemaphore();
    }

    @Override
    public void run() {
        try {
            this.semaphore.acquire();
            vaccinesManagerIn.addVaccines(vaccine.getVaccineType(), vaccine.getAmount());
            this.semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
