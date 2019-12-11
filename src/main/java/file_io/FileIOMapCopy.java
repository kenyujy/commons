package file_io;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileIOMapCopy {

    /**
     * 使用直接缓冲区来完成文件的复制【内存映射文件】
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        FileChannel inChannel = FileChannel.open(Paths.get("c:/tools/a.txt"), StandardOpenOption.READ );
        FileChannel outChannel = FileChannel.open(
                Paths.get("c:/tools/aa.txt"),
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE,
                StandardOpenOption.READ);
        // 获取内存映射文件
        MappedByteBuffer inMap = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMap = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
        byte[] b = new byte[inMap.limit()];
        // 从磁盘文件中获取数据写入到b字节数组中
        inMap.get(b);
        // 将b字节数组中的数据写入到磁盘文件中
        outMap.put(b);

        inChannel.close();
        outChannel.close();
    }
}
