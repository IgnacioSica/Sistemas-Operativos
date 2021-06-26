import Utils.Source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class VaccinationRequestReader implements Runnable {
    private Moments moments;
    private Source source;

    public VaccinationRequestReader(Moments moments, Source source){
        this.source = source;
        this.moments = moments;
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

    @Override
    public void run() {
        System.out.println("Reader: " + this.source.name() + " started");
        //loadRequests(this.source)
        System.out.println("Reader: " + this.source.name() + " finished");
        moments.finish();
    }
}
