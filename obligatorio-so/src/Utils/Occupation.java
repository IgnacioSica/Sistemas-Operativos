package Utils;

public enum Occupation {
    MEDICO_PRIMERA_LIENA("medico primera linea", Vaccine.PFIZER),
    MEDICO_SEGUNDA_LIENA("medico segunda linea", Vaccine.PFIZER);

    private final String description;
    private final Vaccine vaccine;

    private Occupation(String description, Vaccine vaccine){
        this.description = description;
        this.vaccine = vaccine;
    }
    public String getDescription(){
        return description;
    }
    public Vaccine getVaccine(){
        return this.vaccine;
    }
}
