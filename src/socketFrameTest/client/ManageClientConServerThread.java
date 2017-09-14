/** 
* @author chenhuowei: 
* @version 创建时间：2017年7月20日 上午10:37:05 
* 类说明 
*/ 
package socketFrameTest.client;

import java.util.HashMap;

public class ManageClientConServerThread {

private static HashMap<Integer, ClientConServerThread> serverThreadMap = new HashMap<Integer,ClientConServerThread>();

//把创建好的ClientConServerThread放入到hm

public static void addClientConServerThread(int userId,ClientConServerThread ccst)

{

	serverThreadMap.put(userId, ccst);

}

//可以通过userId取得该线程 

public static ClientConServerThread getClientConServerThread(int userId)

{

return (ClientConServerThread)serverThreadMap.get(userId);

}

}

