import java.io.IOException;
import java.util.concurrent.Semaphore;

public class VaccineReader extends ConcreteReader<Vaccine> implements Runnable {
    private Vaccine vaccines;
    private final IVaccinesManagerIn vaccinesManagerIn;
    private Semaphore semaphoreToWrite;

    VaccineReader(String source, Semaphore semaphore, String moment, IVaccinesManagerIn vaccinesManagerIn) {
        super(source, moment, semaphore);

        this.vaccinesManagerIn = vaccinesManagerIn;
    }

    @Override
    public void run() {
        try {
            this.semaphoreToRead.acquire();
            vaccines = readVaccine(this.sourcePath, this.moment);
            this.semaphoreToRead.release();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        VaccineProcessor vaccineProcessor = new VaccineProcessor(vaccines, vaccinesManagerIn);
        new Thread(vaccineProcessor).start();
    }

    private Vaccine readVaccine(String source, String moment) throws IOException, InterruptedException {
        String data = this.readFromSource(source, moment);

        return !data.isEmpty() ? parseData(data) : null;
    }

    @Override
    protected Vaccine parseData(String data) {
        return null;
    }
}
