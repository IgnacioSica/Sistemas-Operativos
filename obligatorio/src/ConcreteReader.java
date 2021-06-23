import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class ConcreteReader<T> {
    protected String sourcePath;
    protected String moment;

    ConcreteReader(String sourcePath, String moment, Semaphore semaphore) {
        this.sourcePath = sourcePath;
        this.moment = moment;
    }

    protected static String readFromSource(String path, String moment) throws IOException {
        File source = new File(path);
        Scanner scanner = new Scanner(source);
        String data = "";
        boolean deleteFirstLine = false;

        if (scanner.hasNext())
            data = scanner.nextLine();

        if (!data.isEmpty() && data.contains(moment))
            deleteFirstLine = true;

        scanner.close();

        if (deleteFirstLine)
            deleteFirstLine(path);

        return data;
    }

    private static void deleteFirstLine(String sourcePath) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(sourcePath, "rw");
        long writePosition = raf.getFilePointer();
        raf.readLine();
        long readPosition = raf.getFilePointer();

        byte[] buff = new byte[1024];
        int n;
        while (-1 != (n = raf.read(buff))) {
            raf.seek(writePosition);
            raf.write(buff, 0, n);
            readPosition += n;
            writePosition += n;
            raf.seek(readPosition);
        }
        raf.setLength(writePosition);
        raf.close();
    }

    protected String readFromSourceConcurrent(String path, String moment) throws InterruptedException, IOException {
        throw new UnsupportedOperationException();
    }

    protected T parseData(String data) {
        throw new UnsupportedOperationException();
    }
}
