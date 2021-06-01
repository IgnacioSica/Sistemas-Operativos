import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.Semaphore;

public class main {
    public static void main(String[] args) {

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        Semaphore requestSem = new Semaphore(1);
        Semaphore vaccinesSem = new Semaphore(1);
        VaccinationCenter center1 = new VaccinationCenter("Antel Arena", 10);

        //Inicializamos los planners
        VaccinePlanner vaccinePlanner = new VaccinePlanner();
        RequestPlanner requestPlanner = new RequestPlanner();
        
        //Inicializamos los semaforos de entrada de los planners
        Semaphore requestProcessorSemaphore = new Semaphore(1);
        Semaphore vaccineProcessorSemaphore = new Semaphore(1);
        
        //Cargamos las solicitudes disponibles
        RequestsReader.loadRequests(requestProcessorSemaphore, requestPlanner);

        //Cargamos las vacunas disponibles
        VaccinesReader.loadVaccines(vaccineProcessorSemaphore, vaccinePlanner);


        //Inicializamos el semaforo del NotificationWriter
        Semaphore notificationWriterSem = new Semaphore(1);
        
        //Definimos y corremos el scheduler
        Scheduler scheduler = new Scheduler(center1, requestSem, vaccinesSem, requestPlanner, vaccinePlanner, notificationWriterSem);
        scheduler.run();

        // Request request1 = new Request("mujer de 85", 85, true, false, false, false, 1);
        // Request request2 = new Request("hombre de 85", 85, false, false, false, false, 1);
        // Request request3 = new Request("hombre medico joven", 30, false, true, false, false, 1);
        // Request request4 = new Request("hombre joven 30", 30, false, false, false, false, 1);
        // Request request5 = new Request("hombre joven 27", 27, false, false, false, false, 0);
        // Request request6 = new Request("hombre joven 26", 26, false, false, false, false, 2);
        // Request request7 = new Request("hombre medico no joven", 50, false, true, false, false, 1);
        // Request request8 = new Request("hombre medico joven con enf.", 30, false, true, false, false, 3);

        // requestPlanner.addRequest(request1);
        // requestPlanner.addRequest(request2);
        // requestPlanner.addRequest(request3);
        // requestPlanner.addRequest(request4);
        // requestPlanner.addRequest(request5);
        // requestPlanner.addRequest(request6);
        // requestPlanner.addRequest(request7);
        // requestPlanner.addRequest(request8);

        // requestPlanner.printByLevel();
        // Request requestByPriority = requestPlanner.extractRequestByPriority();
        // System.out.println("SE ESTRAJO AL USUARIO: " + requestByPriority.name);
        // requestPlanner.printByLevel();
       }
}
