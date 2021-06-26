public class main {
    public static void main(String [] args){
        RequestPlanner rPlanner = new RequestPlanner();
        VaccinePlanner vPlanner = new VaccinePlanner();
        Moments moments = new Moments(100, rPlanner, vPlanner);
        new Thread(moments).start();
    }
}
