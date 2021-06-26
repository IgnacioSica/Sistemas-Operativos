import Interfaces.IVaccinationRequest;
import Utils.*;

public class VaccinationRequest implements IVaccinationRequest {
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
        this.vaccine = occupation.getVaccine() != null ? occupation.getVaccine() : getVaccineByAge(age);
        this.medicalCondition = medicalCondition;
        this.state = State.PENDING;
    }

    private Vaccine getVaccineByAge(int age) {
        if(age >= 80)
            return Vaccine.PFIZER;
        return Vaccine.SINOVAC;
    }

    public void updateRequest(String key, Priority priority) {
        this.key = key;
        this.priority = priority;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Vaccine getVaccine() {
        return this.vaccine;
    }

    @Override
    public Priority getPriority() {
        return this.priority;
    }
}
