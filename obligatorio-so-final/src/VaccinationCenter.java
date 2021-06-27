import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public enum VaccinationCenter {
    Center_A(10),
    Center_B(5),
    Center_C(13),
    Center_D(7),
    Center_E(8),
    Center_F(9);

    private final int availability;
    public final Map<Integer, List<Request>> second_doses = new HashMap<>();

    private VaccinationCenter(int availability) {
        this.availability = availability;
        for (int i = 0; i < 180; i++) {
            second_doses.put(i, new LinkedList<>());
        }
    }

    public int getAvailability() {
        return availability;
    }
}
