import java.io.IOException;
import java.util.concurrent.Semaphore;

public class RequestReader extends ConcreteReader<Request> implements Runnable {
    private final IRequestPlannerIn requestPlannerIn;

    RequestReader(String source, String moment, Semaphore semaphore, IRequestPlannerIn requestPlannerIn) {
        super(source, moment, semaphore);

        this.requestPlannerIn = requestPlannerIn;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = readRequest(sourcePath, moment);
                if (request == null)
                    break;

                RequestProcessor requestProcessor = new RequestProcessor(request, requestPlannerIn);
                new Thread(requestProcessor).start();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Request readRequest(String source, String moment) throws IOException, InterruptedException {
        String data = this.readFromSource(source, moment);

        return !data.isEmpty() ? parseData(data) : null;
    }

    @Override
    protected Request parseData(String data) {
        return null;
    }
}
