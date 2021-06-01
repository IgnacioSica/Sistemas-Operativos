public class Request {
    String name;
    String cedula;
    int age;
    String departamento;
    boolean sexIsFemale;
    boolean healthCareWorkerFirstLine;
    boolean healthCareWorkerSecondLine;
    boolean essentialWorker;
    String chronicHealthConditions;
    double priorityScore;
    int priorityLevel;
    Request(String newName, String newCedula, int newAge, String newDepartamento, boolean newSexIsFemale, boolean newHealthCareWorkerFirstLine, boolean newHealthCareWorkerSecondLine, boolean newEssentialWorker, String newChronicHealthConditions) {
        name = newName;
        cedula = newCedula;
        age = newAge;
        departamento = newDepartamento;
        sexIsFemale = newSexIsFemale;
        healthCareWorkerFirstLine = newHealthCareWorkerFirstLine;
        healthCareWorkerSecondLine = newHealthCareWorkerSecondLine;
        essentialWorker = newEssentialWorker;
        chronicHealthConditions = newChronicHealthConditions;
        priorityScore = getPriorityScore();
        priorityLevel = getPriorityLevel();
    }

    static Request fromString(String data) {
        var dataFields = data.split(",");
        String newCedula = dataFields[0];
        String newName = dataFields[1] + " " + dataFields[2];
        boolean newSexIsFemale = dataFields[3].equals("Female");
        int newAge = Integer.parseInt(dataFields[4]);
        String newDepartamento = dataFields[5];
        boolean newHealthCareWorkerFirstLine = dataFields[6].equals("personal de la salud(primera línea)");
        boolean newHealthCareWorkerSecondLine = dataFields[6].equals("personal de la salud(segunda línea)");
        boolean newEssentialWorker = dataFields[6].equals("trabajador escenciales");
        String newChronicHealthConditions = dataFields[7];
        Request newRequest = new Request(newName, newCedula, newAge, newDepartamento, newSexIsFemale, newHealthCareWorkerFirstLine, newHealthCareWorkerSecondLine, newEssentialWorker, newChronicHealthConditions);
        return newRequest;
    }

    double getPriorityScore() {
        double score = age;
        if (chronicHealthConditions != "n/a") {
            score = score * 1.1;
        }
        if (sexIsFemale) {
            score = score * 0.93;
        }
        return score;
    }

    int getPriorityLevel() {
        if (healthCareWorkerFirstLine) {
            return 0;
        } else if (healthCareWorkerSecondLine) {
            return 1;
        } else if (age >= 80) {
            return 2;
        } else if (age >= 70) {
            return 3;
        } else if (age >= 60) {
            return 4;
        } else if (essentialWorker) {
            return 5;
        } else {
            return 6;
        }
    }
}
