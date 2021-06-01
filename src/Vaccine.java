public class Vaccine {
    //AbstractMap.SimpleEntry<String, Integer> vaccine;
    private final String vaccineType;
    private final int amount;

    Vaccine(String vaccineType, int amount) {
        this.vaccineType = vaccineType;
        this.amount = amount;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public int getAmount() {
        return amount;
    }
}
