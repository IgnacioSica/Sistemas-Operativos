import java.util.concurrent.Semaphore;

public class RequestManager extends Thread{
    RequestManager(String data, Semaphore requestProcessorSemaphore, RequestPlanner requestPlanner){
        requestData = data;
        requestProcessorSem = requestProcessorSemaphore;
        planner = requestPlanner;
    }

    String requestData;
    Semaphore requestProcessorSem;
    RequestPlanner planner;

    @Override
    public void run(){
        try{
            Request request = Request.fromString(requestData);
            requestProcessorSem.acquire();
            planner.addRequest(request);
            System.out.println("added user "+ request.cedula);
            requestProcessorSem.release();
        }catch(Exception e){
            System.out.println("RequestManager error");
        }
    }
    
}
