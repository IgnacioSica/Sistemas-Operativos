import java.util.concurrent.Semaphore;

public class Notifier extends Thread {

    Request request;
    String centerName;
    Semaphore notificationWriterSem;
    Notifier(Request scheduledRequest, String scheduledCenterName, Semaphore notificationWriterSemaphore) {
        request = scheduledRequest;
        centerName = scheduledCenterName;
        notificationWriterSem = notificationWriterSemaphore;
    }

    @Override
    public void run() {
        try {
            notificationWriterSem.acquire();
            NotificationWriter.WriteNotification(request.cedula, centerName);
            notificationWriterSem.release();
            System.out.println("se agendo al usuerio " + request.cedula + " en el centro " + centerName + " con priorityLevel: " + request.priorityLevel + " y priorityScore: " + request.priorityScore);
        } catch (Exception e) {

        }
    }
}
