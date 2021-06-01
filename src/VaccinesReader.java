import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.Semaphore;

public class VaccinesReader {
    
    static void loadVaccines(Semaphore vaccineProcessorSemaphore, VaccinePlanner vaccinePlanner){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try{
            archivo = new File("/Users/fmehues/Desktop/fede/Sistemas-Operativos/input/Entrada de vacunas.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Read file
            String linea;
            while((linea=br.readLine())!=null){
                VaccineManager manager = new VaccineManager(linea, vaccineProcessorSemaphore, vaccinePlanner);
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
    }
}
