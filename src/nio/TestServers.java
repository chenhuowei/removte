package nio;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/** 
 * @author chenhuowei: 
 * @version 创建时间：2017年7月14日 下午4:09:11 
 * 类说明 
 */

public class TestServers {

	private ThreadLocal<Socket> local = new ThreadLocal<Socket>();
	private static volatile Map<Integer, Socket> client = new HashMap<Integer, Socket>();
	public static void main(String[] agrs) {  
        try {  
        	ServerSocket  s = new ServerSocket(8096);  
        	for (int i = 0; i < 3; i++) {
				
	            //设定服务端的端口号  
	            System.out.println("ServerSocket Start:"+s);  
	            //等待请求,此方法会一直阻塞,直到获得请求才往下走  
	            Socket socket = s.accept();  
	            System.out.println("Connection accept socket:"+socket);  
	            client.put(i, socket);
	          //  reply(client.get(i));
        	}
        	for (int i = 0; i < 3; i++) {
        		System.out.println(client.get(i).isClosed()+"--"+client.get(i).isConnected());
        		reply2(client.get(i));
				
			}
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }finally{  
        }  
    }
	private static void reply( Socket socket)
			throws IOException, InterruptedException {
		BufferedReader br = null;  
		PrintWriter pw = null;  
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
		//用于发送返回信息,可以不需要装饰这么多io流使用缓冲流时发送数据要注意调用.flush()方法  
		pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);  
		while(true){  
		    String str = br.readLine();  
		    if(str.equals("END")){  
		        break;  
		    }  
		    System.out.println("Client Socket Message:"+str);  
		    Thread.sleep(300);  
		    pw.println("Message Received from"+socket.getPort());  
		    pw.flush();  
		}  
	}  
	
	private static void reply2( Socket socket)
			throws IOException, InterruptedException {
		BufferedReader br = null;  
		PrintWriter pw = null;  
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
		//用于发送返回信息,可以不需要装饰这么多io流使用缓冲流时发送数据要注意调用.flush()方法  
		pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);  
			Thread.sleep(300);  
			pw.println("map : Message Received from"+socket.getPort());  
			System.out.println("map reply");
			pw.flush();  
	}  
	
	private static class ServerThread extends Thread{
		
	}
	
}
