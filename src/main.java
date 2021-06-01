import java.util.concurrent.Semaphore;

public class main {
    public static void main(String[] args) {
        //Inicializamos el vacunatorio
        VaccinationCenter center1 = new VaccinationCenter("Antel Arena", 10);

        //Inicializamos los planners
        VaccinePlanner vaccinePlanner = new VaccinePlanner();
        RequestPlanner requestPlanner = new RequestPlanner();

        //Inicializamos los semaforos que controlan las entradas a los planners
        Semaphore requestProcessorSemaphore = new Semaphore(1);
        Semaphore vaccineProcessorSemaphore = new Semaphore(1);

        //Cargamos las solicitudes disponibles
        RequestsReader.loadRequests(requestProcessorSemaphore, requestPlanner);

        //Cargamos las vacunas disponibles
        VaccinesReader.loadVaccines(vaccineProcessorSemaphore, vaccinePlanner);

        //Inicializamos el semaforo del NotificationWriter
        Semaphore notificationWriterSem = new Semaphore(1);

        //Inicializamos los semaforos que controlan las salidas de los planners
        Semaphore requestSem = new Semaphore(1);
        Semaphore vaccinesSem = new Semaphore(1);

        //Inicializamos y corremos el scheduler
        Scheduler scheduler = new Scheduler(center1, requestSem, vaccinesSem, requestPlanner, vaccinePlanner, notificationWriterSem);
        scheduler.run();
    }
}
