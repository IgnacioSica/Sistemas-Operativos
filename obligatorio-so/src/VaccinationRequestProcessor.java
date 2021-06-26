import Utils.Priority;
import Utils.Vaccine;

import java.util.concurrent.Semaphore;

public class VaccinationRequestProcessor implements Runnable{
    private final VaccinationRequest request;
    private final ConcretePlanner planner;
    private Semaphore semaphore;

    VaccinationRequestProcessor(VaccinationRequest request, ConcretePlanner concretePlanner){
        this.request = request;
        this.planner = concretePlanner;
    }

    private static String generateKey(VaccinationRequest request){
        int score = calculateScore(request);
        String scoreFormatted = String.format("%04d", score);
        String key = scoreFormatted + request.id;

        return key;
    }

    private static int calculateScore(VaccinationRequest request) {
        return (int) (request.age * request.gender.getMultiplier() * request.medicalCondition.getMultiplier());
    }

    private static Priority getPriority(VaccinationRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
        String key = generateKey(this.request);
        Vaccine vaccine = request.vaccine;
        Priority priority = getPriority(this.request);

        this.request.updateRequest(key, priority);
        this.semaphore = planner.getSemaphore(vaccine.name());

        try {
            semaphore.acquire();
            this.planner.addElement(this.request);
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
