import model.IRequestPlanner;
import model.Priority;
import model.Request;

import java.util.concurrent.Semaphore;

public class RequestProcessor implements Runnable {
    private Request request;
    private IRequestPlanner requestPlanner;
    private Semaphore semaphore;

    RequestProcessor(Request request, IRequestPlanner requestPlanner){
        this.request = request;
        this.requestPlanner = requestPlanner;
    }

    @Override
    public void run() {
        int score = calculateScore(this.request);
        String scoreFormatted = String.format("%04d", score);

        String key = scoreFormatted + this.request.id;
        String line = getLine(request.age, request.occupation);
        Priority priority = getPriotity(request.age, request.occupation);

        request.updateRequest(key, line, priority);

        this.semaphore = requestPlanner.getSemaphore(line);

        try {
            semaphore.acquire();
            requestPlanner.addRequest(request, key, line, priority);
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static int calculateScore(Request request){
        int age = request.age;

        double genderMultiplier = request.gender.equals("male") ? 1 : 0.93;

        double medicalMultiplier = getMedicalScore(request.medicalCondition);

        return (int) (age * genderMultiplier * medicalMultiplier);
    }

    private static double getMedicalScore(String medicalCondition){
        double multiplier;
        switch (medicalCondition) {
            case "Asma":
                multiplier = 1.13;
                break;
            case "Hypertension":
                multiplier = 1.07;
                break;
            case "Obesidad":
                multiplier = 1.31;
                break;
            case "Cancer  (general)" :
                multiplier = 1.07;
                break;
            case "Inmuno deficiencias":
                multiplier = 1.13;
                break;
            case "Diabetes":
                multiplier = 1.36;
                break;
            case "Enfermedad crónica Hepática":
                multiplier = 1.26;
                break;
            case "Enfermedad crónica Renal":
                multiplier = 1.62;
                break;
            case "Condiciones cardiacas severas":
                multiplier = 1.78;
                break;
            default:
                multiplier = 1;
                break;
        }
        return multiplier;
    }

    private static Priority getPriotity(int age, String occupation){
        return Priority.highestPriority;
    }

    private static String getLine(int age, String occupation){
        return "pfizer";
    }
}
