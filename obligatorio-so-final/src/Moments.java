import Utils.Source;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Moments implements Runnable{
    private int running;
    private int moment;
    private int max;

    public Moments(int max){
        this.max = max;
        moment = 1;
    }

    public synchronized void finish(){
        running--;
    }

    @Override
    public void run() {
        while(moment <= max){
            System.out.println(moment);
            for (Source s : Source.values()) {
                int lines = new Random().nextInt(10) + 1;
                VaccinationRequestReader reader = new VaccinationRequestReader(this, s, lines);
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
