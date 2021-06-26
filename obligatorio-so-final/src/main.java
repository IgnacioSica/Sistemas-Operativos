public class main {
    public static void main(String [] args){
        RequestPlanner rPlanner = new RequestPlanner();
        Moments moments = new Moments(100, rPlanner);
        new Thread(moments).start();
    }
}
