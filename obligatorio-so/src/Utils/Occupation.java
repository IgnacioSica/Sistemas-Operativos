package Utils;

public enum Occupation {
    SALUD_PRIMERA_LIENA("Personal de salud - primera línea", Vaccine.PFIZER),
    SALUD_SEGUNDA_LIENA("Personal de salud - segunda línea", Vaccine.PFIZER),
    TRABAJADORES_ESCENCIALES("Trabajadores esenciales", Vaccine.PFIZER),
    POBLACION_GENERAL("Poblacion general", null);

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
