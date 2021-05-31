import java.util.concurrent.Semaphore;

public interface IRequestPlannerOut {
    Semaphore getSemaphore(String line);
    Request getHighestPriorityRequest(String lineName);
}
