/** 
* @author chenhuowei: 
* @version 创建时间：2017年7月20日 上午9:58:09 
* 类说明 
*/ 
package socketFrameTest.server;

import java.util.HashMap;
import java.util.Iterator;

public class ManageClientThread {

	public static HashMap<Integer, SerConClientThread> clientThreadMap =new HashMap<Integer,SerConClientThread>();
	
	public static void addClientThread(int uid,SerConClientThread ct)

	{

		clientThreadMap.put(uid, ct);

	}

	public static SerConClientThread getClientThread(int uid)

	{

	return (SerConClientThread)clientThreadMap.get(uid);

	}

	public static String getAllOnLineUserid()

	{

	Iterator<Integer> it=clientThreadMap.keySet().iterator();

	String res="";//问题所在 “ ” 这个是错的

	while(it.hasNext())

	{

	res+=it.next()+" ";

	}

	return res;

	}
}
