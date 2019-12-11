package io;

/*
 *
 *  FileChannel 从文件中读写数据
 *  DatagramChannel 能通过UDP读写网络中的数据
 *  SocketChannel   能通过TCP读写网络中的数据
 *  ServerSocketChannel 可以监听新进来的TCP连接，像Web服务器那样。对每一个新进来的连接都会创建一个SocketChannel
 *  Java NIO里关键的Buffer实现：
 *  ByteBuffer
 *  CharBuffer
 *  DoubleBuffer
 *  FloatBuffer
 *  IntBuffer
 *  LongBuffer
 *  ShortBuffer
 *  Selector允许单线程处理多个 Channel
 *  SelectionKey
 *  当向Selector注册Channel时，register()方法会返回一个SelectionKey对象。这个对象包含了一些你感兴趣的属性：
 *  -interest集合
 *  -ready集合
 *  -Channel
 *  -Selector
 *  -附加的对象（可选）
 *
 */
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

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
        serverSocketChannel.configureBlocking(false); //设置成非阻塞, 与Selector一起使用时，Channel必须处于非阻塞模式

        this.selector= Selector.open();   //调用Selector.open()方法创建一个Selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); //为channel 注册新的接受连接事件 到选择器
        /*
         * Channel.register()方法会返回一个SelectionKey 对象。
         * 这个对象代表了注册到该Selector的通道。可以通过SelectionKey的channel()方法访问这些对象。
         */
        System.out.println("服务启动");
    }

    public void listenSelector() throws IOException {
        //轮询监听selector
        while (true){
            /*
             * selector -io多路复用, 选择器用于监听多个通道的事件
             * NIO由原来的阻塞读写（占用线程）变成了单线程轮询事件，
             * 找到可以进行读写的网络描述符进行读写。除了事件的轮询是阻塞的（没有可干的事情必须要阻塞），
             * 剩余的I/O操作都是纯CPU操作，没有必要开启多线程。
             * 要使用Selector，得向Selector注册Channel，然后调用它的select()方法。
             * 这个方法会一直阻塞到某个注册的通道有事件就绪。一旦这个方法返回，线程就可以处理这些事件，
             * 事件的例子有如新连接进来，数据接收等。
             */
            int readyChannels =selector.select(); //阻塞在这里, select()方法返回的int值表示有多少通道已经就绪，有可操作的时候才会执行下一步，选择准备就绪的 channel 进行读写
            if(readyChannels == 0) continue;
            Set selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iteKey= selectionKeys.iterator();
            while (iteKey.hasNext()){
                SelectionKey selectionKey= iteKey.next();
                iteKey.remove(); //手动移除，不会自动移除
                //处理请求
                handle(selectionKey);
            }
        }
    }

    private void handle(SelectionKey selectionKey) throws IOException {
        if(selectionKey.isAcceptable()){
            //如果是新连接，则注册一个新的读写处理器
            ServerSocketChannel serverSocketChannel= (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel= serverSocketChannel.accept(); //accept返回一个新进来的连接的 SocketChannel
            socketChannel.configureBlocking(false);  //设置非阻塞
            socketChannel.register(selector, SelectionKey.OP_READ); //把SocketChannel 注册新的读处理器到selector
        }else if (selectionKey.isReadable()){
            //如果可以读，则执行读事件
            SocketChannel socketChannel= (SocketChannel) selectionKey.channel();
            try{
                ByteBuffer buffer= ByteBuffer.allocate(1024); //申请堆外内存空间 ByteBuffer
                int readData= socketChannel.read(buffer);  //从channel 读出数据到缓冲区
                buffer.flip();  //首先读取数据到Buffer，然后反转Buffer,接着再从Buffer中读取数据
                if(readData> 0) {
                    String info = Charset.forName("UTF-8").decode(buffer).toString();
                    System.out.println("收到数据: " + info);
                }
                buffer.clear();
            }catch (Exception e){
                e.printStackTrace();
                String channel= selectionKey.channel().toString();
                System.out.println(channel.substring(channel.lastIndexOf(" ")+1, channel.length()-1)+" 客户端已关闭");
                selectionKey.cancel(); //把当前selectionkey 从selector 移除, 也就是这个channel 移除
            }
        }
    }
}
