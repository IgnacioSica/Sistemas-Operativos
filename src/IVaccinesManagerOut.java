import java.util.AbstractMap;
import java.util.concurrent.Semaphore;

public interface IVaccinesManagerOut {
    AbstractMap.SimpleEntry<String, Integer>  getVaccines(int vaccines);
    Semaphore getSemaphore();
}
