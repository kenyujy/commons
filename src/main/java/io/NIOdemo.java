package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class NIOdemo {

    private Selector selector;

    public static void main(String[] args) throws IOException {
        NIOdemo nIOdemo= new NIOdemo();
        nIOdemo.initServer(8888);
        nIOdemo.listenSelector();
    }

    public void initServer(int port) throws IOException {

        ServerSocketChannel serverSocketChannel= ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false); //设置成非阻塞

        this.selector= Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); //注册新的读处理器 到选择器
        System.out.println("服务启动");
    }

    public void listenSelector() throws IOException {
        //轮询监听selector
        while (true){
            //等待连接
            //selector 多路复用, 选择准备就绪的 channel 进行读写
            /*
             *NIO由原来的阻塞读写（占用线程）变成了单线程轮询事件，
             * 找到可以进行读写的网络描述符进行读写。除了事件的轮询是阻塞的（没有可干的事情必须要阻塞），
             * 剩余的I/O操作都是纯CPU操作，没有必要开启多线程。
             */
            this.selector.select();  //阻塞在这里, 等待事件到来，有可操作的时候才会执行下一步，选择准备就绪的 channel 进行读写
            Iterator<SelectionKey> iteKey= this.selector.selectedKeys().iterator();
            while (iteKey.hasNext()){
                SelectionKey selectionKey= iteKey.next();
                iteKey.remove();
                //处理请求
                handle(selectionKey);
            }
        }
    }

    private void handle(SelectionKey selectionKey) throws IOException {
        if(selectionKey.isAcceptable()){
            //如果是新连接，则注册一个新的读写处理器
            ServerSocketChannel serverSocketChannel= (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel= serverSocketChannel.accept();
            socketChannel.configureBlocking(false);  //设置非阻塞模式
            socketChannel.register(selector, SelectionKey.OP_READ); //把SocketChannel 注册新的读处理器到selector
        }else if (selectionKey.isReadable()){
            //如果可以读，则执行读事件
            SocketChannel socketChannel= (SocketChannel) selectionKey.channel();
            ByteBuffer buffer= ByteBuffer.allocate(1024); //申请堆外内存空间
            int readData= socketChannel.read(buffer);
            buffer.flip();
            if(readData> 0){
                String info= Charset.forName("UTF-8").decode(buffer).toString();
                System.out.println("收到数据: "+ info);
            }else {
                System.out.println("客户端已关闭");
                selectionKey.cancel();
            }
        }
    }
}
