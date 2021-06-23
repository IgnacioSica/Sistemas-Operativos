import Utils.Vaccine;

public class VaccinationRequestProcessor implements Runnable{
    private final VaccinationRequest request;

    VaccinationRequestProcessor(VaccinationRequest request){
        this.request = request;
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

    @Override
    public void run() {
        String key = generateKey(this.request);
        Vaccine vaccine = request.vaccine;

    }
}
