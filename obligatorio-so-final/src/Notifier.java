import java.io.FileWriter;
import java.io.PrintWriter;

public class Notifier extends Thread{

    private void writeNotification(String requestId, String centerId)
    {
        FileWriter fileWriter = null;
        PrintWriter pw = null;
        try
        {
            fileWriter = new FileWriter("src/Usuarios agendados.txt", true);
            pw = new PrintWriter(fileWriter);
            pw.println("usuario: " + requestId + ", centro: "+ centerId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Close File
           if (null != fileWriter){
            fileWriter.close();
           }
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }

    Notifier(Request request, String centerName){
        this.request = request;
        this.centerName = centerName;
    }
    Request request;
    String centerName;

    @Override
    public void run(){
        try{
            writeNotification(request.cedula, centerName);
            System.out.println("            Se agendo la vacunacion del usuario " + request.cedula + " en el " + centerName + " en el nivel " + request.priorityLevel + " con un puntaje de " + request.priorityScore);
        }catch(Exception e){

        }
    }
}