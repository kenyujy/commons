package file_io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileIOCopy {

    /**
     * FileChannel实现文件复制功能
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("c:/tools/a.txt");
        FileOutputStream out = new FileOutputStream("c:/tools/a-copy1.txt");
        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = out.getChannel();

        // 分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 将通道中的数据存入缓冲区中
        while(inChannel.read(byteBuffer)!=-1){
            byteBuffer.flip();// 切换到读取模式
            outChannel.write(byteBuffer); // 将缓冲区的数据写入到输出通道中
            byteBuffer.clear(); // 清空缓冲区
        }

        outChannel.close();
        inChannel.close();
    }
}
