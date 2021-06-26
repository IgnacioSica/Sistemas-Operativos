import Utils.Source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class VaccinationRequestReader extends Thread {
    private Moments moments;
    private Source source;
    private int lines;
    //private RequestPlanner planner;

    public VaccinationRequestReader(Moments moments, Source source, int lines/*, RequestPlanner planner, int n*/){
        this.source = source;
        this.moments = moments;
        this.lines = lines;
        //this.planner = planner;
    }

    /*private void loadRequests(Semaphore requestProcessorSemaphore, RequestPlanner requestPlanner){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try{
            archivo = new File("/Users/fmehues/Desktop/fede/Sistemas-Operativos/input/Solicitudes de agenda.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Read file
            String linea;
            while((linea=br.readLine())!=null){
                RequestManager manager = new RequestManager(linea, requestProcessorSemaphore, requestPlanner);
                manager.run();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            // Close file
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }*/

    private int random(){
        int rand = (int) (Math.random() * 200);

        return (rand);
    }

    @Override
    public void run() {
        System.out.println("Reader: " + this.source.name() + " started in line " + this.source.getCurrentLine());
        this.source.addLines(this.lines);
        try {
            TimeUnit.MILLISECONDS.sleep(random());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //loadRequests(this.source, this.planner);
        System.out.println("Reader: " + this.source.name() + " finished in line "+ this.source.getCurrentLine());
        moments.finish();
    }
}
