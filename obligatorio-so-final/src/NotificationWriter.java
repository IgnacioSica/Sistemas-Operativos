import java.io.FileWriter;
import java.io.PrintWriter;

public class NotificationWriter {
    static void WriteNotification(String requestId, String centerId, String dosis)
    {
        FileWriter fileWriter = null;
        PrintWriter pw = null;
        try
        {
            fileWriter = new FileWriter("src/Usuarios agendados.txt", true);
            pw = new PrintWriter(fileWriter);
            pw.println("usuario: " + requestId + ", centro: "+ centerId + ", dosis: " + dosis);
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
}
