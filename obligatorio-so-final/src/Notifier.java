public class Notifier extends Thread{

    Notifier(Request request, String centerName, String dosis){
        this.request = request;
        this.centerName = centerName;
        this.dosis = dosis;
    }
    Request request;
    String centerName;
    String dosis;

    @Override
    public void run(){
        try{
            NotificationWriter.WriteNotification(request.cedula, centerName, dosis);
            System.out.println("            Se agendo la vacunacion del usuario " + request.cedula + " en el " + centerName + " en el nivel " + request.priorityLevel + " con un puntaje de " + request.priorityScore + " "+ dosis);
        }catch(Exception e){

        }
    }
}