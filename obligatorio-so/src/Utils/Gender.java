package Utils;

public enum Gender {
    MALE("male", 1.00),
    FEMALE("female", 0.93);

    private final String description;
    private final double multiplier;

    private Gender(String description, double multiplier){
        this.description = description;
        this.multiplier = multiplier;
    }
    public String getName(){
        return description;
    }
    public double getMultiplier(){
        return multiplier;
    }
}
