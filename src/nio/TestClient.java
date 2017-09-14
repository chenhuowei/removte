package nio;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/** 
 * @author chenhuowei: 
 * @version 创建时间：2017年7月17日 下午3:07:54 
 * 类说明 
 */

public class TestClient {

	 public static void main(String[] args) {  
	        Socket socket = null;  
	        BufferedReader br = null;  
	        PrintWriter pw = null;  
	        Socket[] sockets = new Socket[3];
	        try {  
	            //客户端socket指定服务器的地址和端口号  
	        {
	        		socket = new Socket("127.0.0.1", 8091);  
	        		System.out.println("Socket=" + socket);  
					
		            //同服务器原理一样  
		            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(  
		            		socket.getOutputStream())));  
		          //  for (int j = 0; j < 3; j++) {  
		                pw.println("client " + socket.getPort());  
		                pw.flush(); 
		                String str = null;
		                while(true){
		                	br = new BufferedReader(new InputStreamReader(  
		                			socket.getInputStream())); 
		                	if (br.readLine() != null){
		                		
		                		str = br.readLine();
		                		System.out.println(str);  
		                	}
		                }  
		          //  }  
	        	}
	        	
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            System.out.println("close......");  
				//br.close();  
				//.close();  
				//socket.close();    
	        }  
	    }  
	
}
