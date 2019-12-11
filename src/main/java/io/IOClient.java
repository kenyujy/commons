package io;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class IOClient {

    public static void main(String[] args) throws IOException {

        //bioClent();
        nioClent();
    }

    public static void bioClent() throws IOException {

        Socket socket= new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1",8888)); //模拟客户端socket

        boolean flag= true;
        while (flag) {
            Scanner scanner = new Scanner(System.in);  //输入
            String msg = scanner.nextLine();           //读取一行
            if (!"q".equals(msg)) {
                OutputStream os = socket.getOutputStream();
                os.write(msg.getBytes());
            }else flag= false;
        }
        socket.close();
    }

    public static void nioClent() throws IOException {

        SocketChannel socketChannel= SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",8888)); //模拟客户端socket

        boolean flag= true;
        while (flag) {
            Scanner scanner = new Scanner(System.in);  //输入
            String msg = scanner.nextLine();           //读取一行
            if (!"q".equals(msg)) {
                ByteBuffer byteBuffer= ByteBuffer.allocate(48);
                byteBuffer.put(msg.getBytes());
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
            }else flag= false;
        }
        socketChannel.close();
    }

}
