import Utils.Source;

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
                VaccinationRequestReader reader = new VaccinationRequestReader(s.name(),this, s);
                running++;
                new Thread(reader).run();
            }

            //TODO START READERS

            if(moment % 24 == 0){
                //TODO START SCHEDULER INITIALIZER
            }

            while(running > 0){}
            moment++;
        }
    }
}
