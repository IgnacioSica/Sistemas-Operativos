public class RequestManager extends Thread {
    String requestData;
    RequestPlanner planner;
    RequestManager(String data, RequestPlanner requestPlanner) {
        requestData = data;
        planner = requestPlanner;
    }

    @Override
    public void run() {
        try {
            Request request = Request.fromString(requestData);
            planner.semaphore.acquire();
            planner.addRequest(request);
            System.out.println("        added user " + request.cedula);
            planner.semaphore.release();
        } catch (Exception e) {
            System.out.println("        RequestManager error");
        }
    }

}
