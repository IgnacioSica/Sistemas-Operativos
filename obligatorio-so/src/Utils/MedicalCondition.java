package Utils;

public enum MedicalCondition {
    DIABETES("diabetes", 1.36),
    ASMA("asma", 1.13),
    HYPERTENSION("hypertension", 1.07),
    OBESIDAD("obesidad", 1.31),
    CANCER_GENERAL("",1.07),
    INMUNO_DEFICIENCIAS("",1.13),
    ENFERMEDAD_CRONICA_HEPATICA("",1.26),
    ENFERMEDAD_CRONICA_RENAL("",1.62),
    CONDICIONES_CARDIACAS_SEVERAS("",1.78);

    private final String name;
    private final double multiplier;

    private MedicalCondition(String name, double multiplier){
        this.name = name;
        this.multiplier = multiplier;
    }
    public String getName(){
        return name;
    }
    public double getMultiplier(){
        return multiplier;
    }
}
