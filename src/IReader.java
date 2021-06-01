import java.io.FileNotFoundException;
import java.io.IOException;

public interface IReader {
    String readFromSource(String source, String moment) throws InterruptedException, IOException;
}
