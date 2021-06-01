import java.io.FileWriter;
import java.io.PrintWriter;

public class NotificationWriter {
    static void WriteNotification(String requestId, String centerId) {
        FileWriter fileWriter = null;
        PrintWriter pw = null;
        try {
            fileWriter = new FileWriter("./output/Usuarios agendados.txt", true);
            pw = new PrintWriter(fileWriter);
            pw.println("usuario: " + requestId + ", centro: " + centerId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close File
                if (null != fileWriter) {
                    fileWriter.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
