import Utils.*;

public class VaccinationRequest {
    public String id;
    public Gender gender;
    public int age;
    public Occupation occupation;
    public MedicalCondition medicalCondition;

    public String key;
    public Vaccine vaccine;
    public State state;
    public Priority priority;

    public VaccinationRequest(
            String id, Gender gender, int age, Occupation occupation, MedicalCondition medicalCondition) {
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
        this.vaccine = occupation.getVaccine();
        this.medicalCondition = medicalCondition;
        this.state = State.PENDING;
    }

    public void updateRequest(String key, Vaccine vaccine, Priority priority) {
        this.key = key;
        //this.vaccine = vaccine;
        this.priority = priority;
    }
}
