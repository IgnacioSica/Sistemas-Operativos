import java.util.AbstractMap;
import java.util.concurrent.Semaphore;

public interface IVaccinesManagerOut {
    Vaccine  getVaccines(int vaccines);
    Semaphore getSemaphore();
}
