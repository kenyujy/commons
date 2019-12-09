package io;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class IOClient {

    public static void main(String[] args) throws IOException {

        Socket socket= new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1",8888)); //模拟客户端socket

        while (true) {
            Scanner scanner = new Scanner(System.in);  //输入
            String msg = scanner.nextLine();           //读取一行
            OutputStream os= socket.getOutputStream();
            os.write(msg.getBytes());
        }
    }
}
