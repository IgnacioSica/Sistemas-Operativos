import Utils.Source;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Moments implements Runnable{
    private int running;
    private int moment;
    private int max;
    private RequestPlanner rPlanner;
    private VaccinePlanner vPlanner;

    public Moments(int max, RequestPlanner rPlanner, VaccinePlanner vPlanner){
        this.max = max;
        moment = 1;
        this.rPlanner = rPlanner;
        this.vPlanner = vPlanner;
    }

    public synchronized void finish(){
        running--;
    }

    @Override
    public void run() {
        int[] momentsWithVaccineArrival = {1, new Random().nextInt(100) + 1};
        while(moment <= max){
            System.out.println(moment);
            for(var i = 0; i < momentsWithVaccineArrival.length; i++){
                if(moment == momentsWithVaccineArrival[i]){
                    VaccinesReader reader = new VaccinesReader(this, i, vPlanner);
                    running++;
                    new Thread(reader).start();
                }
            }
            for (Source s : Source.values()) {
                int lines = new Random().nextInt(10) + 1;
                VaccinationRequestReader reader = new VaccinationRequestReader(this, s, lines, rPlanner);
                running++;
                new Thread(reader).start();
            }

            if(moment % 24 == 0){

                //running++;
                //TODO START SCHEDULER INITIALIZER
            }

            while(running > 0){
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //System.out.println(running);
            moment++;
        }
    }
}
