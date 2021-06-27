import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class VaccinationRequestReader extends Thread {
    private Moments moments;
    private Source source;
    private int lines;
    private RequestPlanner planner;

    public VaccinationRequestReader(Moments moments, Source source, int lines, RequestPlanner planner) {
        this.source = source;
        this.moments = moments;
        this.lines = lines;
        this.planner = planner;
    }

    private void loadRequests() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            //Esto es solo para testear
            if (source != Source.input_wpp) {
                TimeUnit.MILLISECONDS.sleep(random());
                source.addLines(lines);
                return;
            }
            archivo = new File("src/Solicitudes de agenda " + source.name() + ".txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Read file
            String linea;
            int lineNumber = 0;
            while ((linea = br.readLine()) != null && lineNumber < source.getCurrentLine()) {
                lineNumber++;
            }
            int maxLine = source.getCurrentLine() + lines;
            while ((linea = br.readLine()) != null && source.getCurrentLine() < maxLine) {
                RequestManager manager = new RequestManager(linea, planner);
                manager.run();
                source.addLines(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close file
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private int random() {
        int rand = new Random().nextInt(moments.timeout);

        return (rand);
    }

    public void run() {
        System.out.println("    Reader: " + this.source.name() + " started in line " + this.source.getCurrentLine());
        try {
            TimeUnit.MILLISECONDS.sleep(random());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loadRequests();
        System.out.println("    Reader: " + this.source.name() + " finished before line " + this.source.getCurrentLine());
        moments.ProcessFinished();
    }
}
