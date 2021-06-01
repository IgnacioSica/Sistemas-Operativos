import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class ConcreteReader<T>{
    protected String sourcePath;
    protected String moment;
    protected Semaphore semaphoreToRead;

    ConcreteReader(String sourcePath, String moment, Semaphore semaphore){
        this.sourcePath = sourcePath;
        this.moment = moment;
        this.semaphoreToRead = semaphore;
    }

    protected String readFromSource(String sourcePath, String moment) throws InterruptedException, IOException {
        this.semaphoreToRead.acquire();
        File source = new File(sourcePath);
        Scanner scanner = new Scanner(source);
        String data = "";
        boolean deleteFirstLine = false;

        if(scanner.hasNext())
            data = scanner.nextLine();

        if(!data.isEmpty() && data.contains(moment))
            deleteFirstLine = true;

        scanner.close();

        if(deleteFirstLine)
            deleteFirstLine(sourcePath);

        this.semaphoreToRead.release();

        return data;
    }

    private void deleteFirstLine(String sourcePath) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(sourcePath, "rw");
        //Initial write position
        long writePosition = raf.getFilePointer();
        raf.readLine();
        // Shift the next lines upwards.
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

    protected T parseData(String data){
        throw new UnsupportedOperationException();
    }
}
