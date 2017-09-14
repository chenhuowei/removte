/** 
 * @author chenhuowei: 
 * @version 创建时间：2017年7月17日 下午5:04:17 
 * 类说明 
 */
package nio;
import java.io.*;  
import java.nio.*;  
import java.nio.channels.*;  
import java.nio.charset.*;  
import java.net.*;  
import java.util.*;  
  
import net.sf.json.JSONObject;  
  
public class EchoServer {  
    private Selector selector = null;  
    private ServerSocketChannel serverSocketChannel = null;  
    private int port = 8000;  
    private Charset charset = Charset.forName("UTF-8");  
  
    private Map<String, SocketChannel> serverSocketMap;//记录socket的键值对  
  
    public EchoServer() throws IOException {  
  
        serverSocketMap = new HashMap<String, SocketChannel>();  
  
        selector = Selector.open();  
        serverSocketChannel = ServerSocketChannel.open();  
        serverSocketChannel.socket().setReuseAddress(true);  
        serverSocketChannel.socket().bind(new InetSocketAddress(port));  
        System.out.println("Start");  
    }  
  
    public void accept() {  
        for (;;) {  
            try {  
                SocketChannel socketChannel = serverSocketChannel.accept();  
  
                System.out.println("recive client:"  
                        + socketChannel.socket().getInetAddress() + ":"  
                        + socketChannel.socket().getPort());  
                socketChannel.configureBlocking(false);  
  
                ByteBuffer buffer = ByteBuffer.allocate(1024);  
                synchronized (gate) {  
                    selector.wakeup();  
                    socketChannel.register(selector, SelectionKey.OP_READ  
                            | SelectionKey.OP_WRITE, buffer);  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    private Object gate = new Object();  
  
    public void service() throws IOException {  
        for (;;) {  
            synchronized (gate) {  
            }  
            int n = selector.select();  
  
            if (n == 0)  
                continue;  
            Set<SelectionKey> readyKeys = selector.selectedKeys();  
            Iterator it = readyKeys.iterator();  
            while (it.hasNext()) {  
                SelectionKey key = null;  
                try {  
                    key = (SelectionKey) it.next();  
                    it.remove();  
                    if (key.isReadable()) {  
  
                        receive(key);  
                    }  
                    if (key.isWritable()) {  
  
                        send(key);  
                    }  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    try {  
                        if (key != null) {  
                            key.cancel();  
                            key.channel().close();  
                        }  
                    } catch (Exception ex) {  
                        e.printStackTrace();  
                    }  
                }  
            }// #while  
        }// #while  
    }  
  
    public void send(SelectionKey key) throws IOException {  
  
        ByteBuffer buffer = (ByteBuffer) key.attachment();  
        SocketChannel socketChannel = (SocketChannel) key.channel();  
  
        buffer.flip();  
        String data = decode(buffer);  
        if (data.indexOf("\n") == -1)  
            return;  
  
        String outputData = data.substring(0, data.indexOf("\n") + 1);  
  
        System.out.println("outputData =" + outputData);  
  
        JSONObject jsonObject = JSONObject.fromObject(outputData);  
        String nameString = jsonObject.getString("sender");  
        System.out.println(nameString);  
  
        /*这里实现了，要发给哪个socket*/  
        if (serverSocketMap.get(nameString) == null) {  
  
            serverSocketMap.put(nameString, socketChannel);  
              
            System.out.println(nameString + "--socket save");  
        }  
          
        String toName = jsonObject.getString("reciver");  
          
          
        if (serverSocketMap.get(toName) != null) {  
            socketChannel = serverSocketMap.get(toName);  
              
            String contentString = jsonObject.getString("content");  
            System.out.println("contentString" + contentString);  
            ByteBuffer outputBuffer = encode(outputData);  
  
            while (outputBuffer.hasRemaining()) {  
                socketChannel.write(outputBuffer);  
            }  
              
        }  
      /*-------------------------测试用--------------------------*/  
      String contentString = jsonObject.getString("content");  
      ByteBuffer outputBuffer = encode("echo:" + contentString);  
  
      while (outputBuffer.hasRemaining()) {  
          socketChannel.write(outputBuffer);  
      }  
          
          
          
          
        ByteBuffer temp = encode(outputData);  
        buffer.position(temp.limit());  
        buffer.compact();  
          
  
        if (outputData.equals("bye\r\n")) {  
            key.cancel();  
            socketChannel.close();  
            System.out.println("断开");  
        }  
    }  
  
    public void receive(SelectionKey key) throws IOException {  
  
        ByteBuffer buffer = (ByteBuffer) key.attachment();  
  
        SocketChannel socketChannel = (SocketChannel) key.channel();  
        ByteBuffer readBuff = ByteBuffer.allocate(32);  
        socketChannel.read(readBuff);  
        readBuff.flip();  
  
        buffer.limit(buffer.capacity());  
        buffer.put(readBuff);  
  
    }  
  
    public String decode(ByteBuffer buffer) {  
        CharBuffer charBuffer = charset.decode(buffer);  
        return charBuffer.toString();  
    }  
  
    public ByteBuffer encode(String str) {  
        return charset.encode(str);  
    }  
  
    public static void main(String args[]) throws Exception {  
        final EchoServer server = new EchoServer();  
        Thread accept = new Thread() {  
            public void run() {  
                server.accept();  
            }  
        };  
        accept.start();  
        server.service();  
    }  
}  

