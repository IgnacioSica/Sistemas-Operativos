import java.io.IOException;
import java.util.concurrent.Semaphore;

public class VaccineReader extends ConcreteReader<Vaccine> implements Runnable {
    private final IVaccinesManagerIn vaccinesManagerIn;
    private final Semaphore semaphore;
    private Vaccine vaccines;

    VaccineReader(String source, Semaphore semaphore, String moment, IVaccinesManagerIn vaccinesManagerIn) {
        super(source, moment, semaphore);
        this.semaphore = semaphore;
        this.vaccinesManagerIn = vaccinesManagerIn;
    }

    @Override
    public void run() {
        try {
            this.semaphore.acquire();
            vaccines = readVaccine(this.sourcePath, this.moment);
            this.semaphore.release();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        VaccineProcessor vaccineProcessor = new VaccineProcessor(vaccines, vaccinesManagerIn);
        new Thread(vaccineProcessor).start();
    }

    private Vaccine readVaccine(String source, String moment) throws IOException, InterruptedException {
        String data = this.readFromSourceConcurrent(source, moment);

        return !data.isEmpty() ? parseData(data) : null;
    }

    @Override
    protected String readFromSourceConcurrent(String path, String moment) throws InterruptedException, IOException {
        this.semaphore.acquire();
        String data = readFromSource(path, moment);
        this.semaphore.release();
        return data;
    }

    @Override
    protected Vaccine parseData(String data) {
        return null;
    }
}
