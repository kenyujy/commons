package file_io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

public class FileIO {

    public static void main(String[] args) throws IOException, InterruptedException {

        String filePath= "C:\\Users\\ken\\Documents\\nio-data.txt";
        RandomAccessFile aFile = new RandomAccessFile(filePath, "rw");

        writeFile(aFile);
        TimeUnit.SECONDS.sleep(1);
        readFile(filePath);

    }

    public static void readFile(String filePath) throws IOException {

        FileChannel inFileChannel= FileChannel.open(Paths.get(filePath), StandardOpenOption.READ);

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) inFileChannel.size());
        byteBuffer.clear();
        int bytesRead= inFileChannel.read(byteBuffer);
        while (bytesRead!= 0){
            System.out.print(new String(byteBuffer.array(),"UTF-8"));
            bytesRead= inFileChannel.read(byteBuffer);
        }
        inFileChannel.close();
    }

    public static void writeFile(RandomAccessFile outFile) throws IOException {

        FileChannel outChannel = outFile.getChannel();
        String newData = "666"+"\n";
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        byteBuffer.clear();
        byteBuffer.put(newData.getBytes());
        byteBuffer.flip();
        while(byteBuffer.hasRemaining()) {
            outChannel.write(byteBuffer, outChannel.size());
        }
        outChannel.close();
    }
}
