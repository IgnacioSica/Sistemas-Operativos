import java.util.concurrent.Semaphore;

public class VaccineProcessor implements Runnable{
    private Vaccine vaccine;
    private IVaccinesManagerIn vaccinesManagerIn;
    private Semaphore semaphore;

    VaccineProcessor(Vaccine vaccine, IVaccinesManagerIn vaccinesManagerIn){
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
