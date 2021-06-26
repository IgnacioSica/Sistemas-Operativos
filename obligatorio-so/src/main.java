import Utils.Vaccine;

public class main {
    public static void main (String [] args){
        ConcretePlanner planner = new ConcretePlanner();

        for (Vaccine v : Vaccine.values()) {
            planner.addQueue(v.name());
            System.out.println(v.name());
        }



    }
}
