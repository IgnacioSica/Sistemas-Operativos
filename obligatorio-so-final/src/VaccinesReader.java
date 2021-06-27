import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

public class VaccinesReader extends Thread {
    private Moments moments;
    private int lineIndex;
    private VaccinePlanner planner;

    public VaccinesReader(Moments moments, int lineIndex, VaccinePlanner planner) {
        this.moments = moments;
        this.lineIndex = lineIndex;
        this.planner = planner;
    }

    private void loadRequests() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File("src/Vacunas.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Read file
            String linea;
            int lineNumber = 0;
            while ((linea = br.readLine()) != null && lineNumber <= lineIndex) {
                if (lineNumber == lineIndex) {
                    VaccineManager manager = new VaccineManager(linea, planner);
                    manager.run();
                }
                lineNumber++;
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
        int rand = (int) (Math.random() * 200);

        return (rand);
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(random());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loadRequests();
        System.out.println("    Se cargo el lote de vacunas " + lineIndex);
        moments.ProcessFinished();
    }
}
