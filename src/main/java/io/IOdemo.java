package io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOdemo {

    public static void main(String[] args) throws IOException {
        ExecutorService threadPool= Executors.newFixedThreadPool(8);

        ServerSocket serverSocket= new ServerSocket(8888);

        while (true) { //死循环等待 socket连接
            Socket socket = serverSocket.accept(); //服务端ServerSocket 等待连接, 一旦有新连接就返回socket 交给线程池
            System.out.println("有客户端连接上");  //telnet localhost 8888 , ctrl+] send abc
            threadPool.execute(()->
                    new MySocketHandler(socket).run()); //把线程上下文socket传递进去
        }
    }
}

class MySocketHandler implements Runnable{

    private Socket socket;

    public MySocketHandler(Socket socket){
        this.socket= socket;
    }
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted() && !socket.isClosed()) { //死循环处理读写事件
            InputStream inputStream = null;
            try {
                inputStream = socket.getInputStream();
                byte[] b = new byte[1024];
                int data = inputStream.read(b);  //阻塞等待读取
                if (data != -1) {
                    String info = new String( b, 0, data, "UTF-8");
                    System.out.println(info);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
